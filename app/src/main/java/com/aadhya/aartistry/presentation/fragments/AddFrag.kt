package com.aadhya.aartistry.presentation.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.aadhya.aartistry.R
import com.aadhya.aartistry.data.utils.Utils
import com.aadhya.aartistry.databinding.LayoutAddFragmentBinding

class AddFrag : Fragment() {
    private lateinit var _binding: LayoutAddFragmentBinding
    private val GALLERY_REQUEST_CODE = 123
    private val PERMISSION_REQUEST_CODE = 456
    var selectedCategory = ""
    var selectedSubCategory = ""
    private lateinit var imgUri: String

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        init()
        imgUri = ""
    }

    private fun init() {
        getSpinnerAdapter()
        initListners()

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initListners() {
        _binding.mainCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*> ,
                view: View? ,
                position: Int ,
                id: Long ,
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                if (selectedItem == "Select Category") {
                    selectedCategory = ""
                } else {
                    selectedCategory = selectedItem
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        _binding.subCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*> ,
                view: View? ,
                position: Int ,
                id: Long ,
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                if (selectedItem == "Select Subcategory") {
                    selectedSubCategory = ""
                } else {
                    selectedSubCategory = selectedItem
//                    Toast.makeText(requireContext() , selectedSubCategory , Toast.LENGTH_SHORT)
//                        .show()
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        _binding.btnUpload.setOnClickListener {
            if (_binding.imgAddView.drawable != null && selectedCategory.isEmpty() && selectedSubCategory.isEmpty()) {
                checkImage()
            } else {
                checkCondition()
            }
        }

    }

    private fun checkCondition() {
        when {
            _binding.imgAddView.drawable == null -> {
                checkImage()
            }

            selectedCategory.isEmpty() -> {
                Toast.makeText(
                    requireContext() , "Please Select Category" , Toast.LENGTH_SHORT
                ).show()
            }

            selectedSubCategory.isEmpty() -> {
                Toast.makeText(
                    requireContext() , "Please Select SubCategory" , Toast.LENGTH_SHORT
                ).show()
            }

            _binding.edtName.text.isEmpty() -> {
                Toast.makeText(requireContext() , "Please Enter Name" , Toast.LENGTH_SHORT).show()
            }

            else -> {
                addData()
            }
        }
    }

    private fun checkImage() {
        val expectedDrawable = resources.getDrawable(R.drawable.ic_upload , null)
        val currentDrawable = _binding.imgAddView.drawable

        val bitmap1 = expectedDrawable.toBitmap()
        val bitmap2 = currentDrawable.toBitmap()
        var isSame = bitmap1.sameAs(bitmap2)

        if (isSame) Toast.makeText(
            requireContext() , "Choose Image" , Toast.LENGTH_SHORT
        ).show()
        else checkCondition()
    }

    private fun addData() {
        if (_binding.edtName.text.isEmpty() && selectedSubCategory.isEmpty() && selectedSubCategory.isEmpty()) {
            checkCondition()
        } else {
            val category = selectedCategory
            val subcategory = selectedSubCategory
            val name = _binding.edtName.text
            val imgView = imgUri
            Log.d("DATA" , "$category $subcategory $name $imgView")
            Toast.makeText(requireContext() , "Done" , Toast.LENGTH_SHORT).show()
            _binding.edtName.text.clear()
        }
    }

    private fun getSpinnerAdapter() {
        val subCateAdapter = ArrayAdapter(
            requireContext() , android.R.layout.simple_spinner_dropdown_item , Utils.subCategory
        )
        _binding.subCategory.adapter = subCateAdapter
        _binding.subCategory.isEnabled = true

        val mainCateAdapter = ArrayAdapter(
            requireContext() , android.R.layout.simple_spinner_dropdown_item , Utils.mainCategory
        )
        _binding.mainCategory.adapter = mainCateAdapter
        _binding.mainCategory.isEnabled = true
    }

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? , savedInstanceState: Bundle? ,
    ): View {
        _binding = LayoutAddFragmentBinding.inflate(inflater , container , false)
        _binding.btnAddImage.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext() , Manifest.permission.READ_EXTERNAL_STORAGE
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

        return _binding.root
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent , GALLERY_REQUEST_CODE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int , resultCode: Int , data: Intent?) {
        super.onActivityResult(requestCode , resultCode , data)
        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            imgUri = data?.data.toString()
            _binding.imgAddView.setImageURI(data?.data)
            addData()
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