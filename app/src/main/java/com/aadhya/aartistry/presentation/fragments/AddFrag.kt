package com.aadhya.aartistry.presentation.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.aadhya.aartistry.R
import com.aadhya.aartistry.data.utils.Utils
import com.aadhya.aartistry.databinding.LayoutAddFragmentBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class AddFrag : Fragment() {
    private lateinit var _binding: LayoutAddFragmentBinding
    private val GALLERY_REQUEST_CODE = 123
    private val PERMISSION_REQUEST_CODE = 456
    var selectedCategory = ""
    var selectedSubCategory = ""
    private var imgUri: String = ""
    private lateinit var mDatabaseReference: DatabaseReference

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        init()
        mDatabaseReference = FirebaseDatabase.getInstance().reference
    }

    private fun init() {
        getSpinnerAdapter()
        initListeners()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initListeners() {
        _binding.mainCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*> ,
                view: View? ,
                position: Int ,
                id: Long ,
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                selectedCategory = if (selectedItem == "Select Category") "" else selectedItem
                updateSubCategorySpinner()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        _binding.subCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*> ,
                view: View? ,
                position: Int ,
                id: Long ,
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                selectedSubCategory = if (selectedItem == "Select Subcategory") "" else selectedItem
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        _binding.btnUpload.setOnClickListener {
            when {
                _binding.imgAddView.drawable != null -> checkImage()
                selectedCategory.isEmpty() -> {
                    Toast.makeText(requireContext() , "Please Select Category" , Toast.LENGTH_SHORT)
                        .show()
                }

                selectedCategory == "Mehandi Design" && selectedSubCategory.isEmpty() -> {
                    Toast.makeText(
                        requireContext() ,
                        "Please Select SubCategory" ,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                _binding.edtName.text.isEmpty() -> {
                    Toast.makeText(requireContext() , "Please Enter Name" , Toast.LENGTH_SHORT)
                        .show()
                }

                else -> uploadImageToFirebaseStorage(imgUri)
            }
        }

        _binding.btnAddImage.setOnClickListener {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
                if (ContextCompat.checkSelfPermission(
                        requireContext() ,
                        Manifest.permission.READ_MEDIA_IMAGES
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        requireActivity() ,
                        arrayOf(Manifest.permission.READ_MEDIA_IMAGES) ,
                        PERMISSION_REQUEST_CODE
                    )
                } else
                    openGallery()
            } else {
                if (ContextCompat.checkSelfPermission(
                        requireContext() ,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        requireActivity() ,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE) ,
                        PERMISSION_REQUEST_CODE
                    )
                } else {
                    openGallery()
                }
            }

        }
    }

    private fun updateSubCategorySpinner() {
        if (selectedCategory == "Mehandi Design") {
            val subCateAdapter = ArrayAdapter(
                requireContext() , android.R.layout.simple_spinner_dropdown_item , Utils.subCategory
            )
            _binding.subCategory.adapter = subCateAdapter
            _binding.subCategory.visibility = View.VISIBLE
            _binding.subCategory.isEnabled = true
        } else {
            _binding.subCategory.visibility = View.GONE
            _binding.subCategory.adapter = null
            _binding.subCategory.isEnabled = false
        }
    }

    private fun checkImage() {
        val expectedDrawable = resources.getDrawable(R.drawable.ic_upload , null)
        val currentDrawable = _binding.imgAddView.drawable

        val bitmap1 = expectedDrawable.toBitmap()
        val bitmap2 = currentDrawable.toBitmap()
        val isSame = bitmap1.sameAs(bitmap2)

        if (isSame) {
            Toast.makeText(requireContext() , "Choose Image" , Toast.LENGTH_SHORT).show()
        } else {
            checkCondition()
        }
    }

    private fun getSpinnerAdapter() {
        val mainCateAdapter = ArrayAdapter(
            requireContext() , android.R.layout.simple_spinner_dropdown_item , Utils.mainCategory
        )
        _binding.mainCategory.adapter = mainCateAdapter
        _binding.mainCategory.isEnabled = true
    }

    override fun onCreateView(
        inflater: LayoutInflater ,
        container: ViewGroup? ,
        savedInstanceState: Bundle? ,
    ): View {
        _binding = LayoutAddFragmentBinding.inflate(inflater , container , false)
        return _binding.root
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*", "video/*"))
        }
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int , resultCode: Int , data: Intent?) {
        super.onActivityResult(requestCode , resultCode , data)
        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            imgUri = data?.data.toString()
            if (imgUri.isNotEmpty()) {
                _binding.imgAddView.setImageURI(data?.data)
            }
        }
    }

    private fun uploadImageToFirebaseStorage(imgUri: String) {
        val storageReference = FirebaseStorage.getInstance().reference
        val imageRef = storageReference.child("images/${UUID.randomUUID()}.jpg")

        imageRef.putFile(imgUri.toUri()).addOnSuccessListener { taskSnapshot ->
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                val downloadUrl = uri.toString()
                saveImageDetailsToDatabase(downloadUrl)
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(
                requireContext() , "Upload failed: ${exception.message}" , Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun saveImageDetailsToDatabase(imageUrl: String) {
        if (_binding.edtName.text.isEmpty()) {
            Toast.makeText(requireContext() , "Please Fill Required Fields.." , Toast.LENGTH_SHORT)
                .show()
        } else {
            val imageDetails = hashMapOf(
                "url" to imageUrl ,
                "name" to _binding.edtName.text.toString() ,
                "category" to selectedCategory ,
                "timestamp" to System.currentTimeMillis()
            )
            if (selectedCategory == "Mehandi Design") {
                imageDetails["subCategory"] = selectedSubCategory
            }
            mDatabaseReference.child("images").push().setValue(imageDetails).addOnSuccessListener {
                Toast.makeText(
                    requireContext() , "Your data has been Uploaded.." , Toast.LENGTH_SHORT
                ).show()
                clearForm()
            }.addOnFailureListener { exception ->
                Toast.makeText(
                    requireContext() ,
                    "Failed to upload data....: ${exception.message}" ,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun clearForm() {
        _binding.edtName.text.clear()
        _binding.imgAddView.setImageResource(R.drawable.ic_upload)
        _binding.mainCategory.setSelection(0)
        _binding.subCategory.setSelection(0)
        _binding.subCategory.visibility = View.GONE
        selectedCategory = ""
        selectedSubCategory = ""
        imgUri = ""
    }

    private fun checkCondition() {
        if (selectedCategory == "Mehandi Design") {
            Toast.makeText(
                requireContext() , "Please Select SubCategory" , Toast.LENGTH_SHORT
            ).show()
        }
        when {
//            _binding.imgAddView.drawable == null -> {
//                checkImage()
//            }

            selectedCategory.isEmpty() -> {
                Toast.makeText(
                    requireContext() , "Please Select Category" , Toast.LENGTH_SHORT
                ).show()
            }

            _binding.edtName.text.isEmpty() -> {
                Toast.makeText(requireContext() , "Please Enter Name" , Toast.LENGTH_SHORT).show()
            }

            else -> {
                uploadImageToFirebaseStorage(imgUri)
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int ,
        permissions: Array<out String> ,
        grantResults: IntArray ,
    ) {
        super.onRequestPermissionsResult(requestCode , permissions , grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                openGallery()
            } else {
                Toast.makeText(requireContext() , "Permission denied" , Toast.LENGTH_SHORT).show()
            }
        }
    }
}
