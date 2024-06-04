package com.aadhya.aartistry.presentation.edit

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.aadhya.aartistry.R
import com.aadhya.aartistry.data.utils.Utils
import com.aadhya.aartistry.databinding.ActivityEditPageBinding
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EditPage : AppCompatActivity() {
    private val GALLERY_REQUEST_CODE = 123
    private val PERMISSION_REQUEST_CODE = 456
    private lateinit var _binding: ActivityEditPageBinding
    private var imgUri: String? = null
    private var timeStamp: String? = null
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityEditPageBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        myRef = FirebaseDatabase.getInstance().reference.child("images")
        imgUri = ""

        _binding.btnUploadImage.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this , Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this ,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE) ,
                    PERMISSION_REQUEST_CODE
                )
            } else {
                openGallery()
            }
        }

        initializeView()
    }

    private fun initializeView() {
        val intent = intent

        val uriString: String? = intent.getStringExtra("image")

        if (uriString.isNullOrEmpty()) {
        } else {
            val imageUri: Uri = Uri.parse(uriString)
            imgUri = uriString.toString()
            Glide.with(this).load(imageUri).into(_binding.imgView)
        }

        val selectedItem = intent.getStringExtra("subCategory")
        timeStamp = intent.getStringExtra("time")

        if (selectedItem != null) {
            getAdapter(selectedItem)
        }

        val mainCategory = intent.getStringExtra("category")
        _binding.txtmainCategory.text = mainCategory
        var name = intent.getStringExtra("name").toString()
        _binding.edtName.setText(name)
        println("NAME IS $name")
        if (mainCategory == "Mehandi Design") {
            _binding.txtsubCategory.visibility = View.VISIBLE
        } else {
            _binding.txtsubCategory.visibility = View.GONE
        }

        _binding.btnEdit.setOnClickListener {
            name = _binding.edtName.text.toString()
            firebaseDataUpdate(
                imgUri , mainCategory , name , selectedItem = selectedItem.toString()
            )
        }

        _binding.btnUploadImage.setOnClickListener {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
                if (ContextCompat.checkSelfPermission(
                        this , Manifest.permission.READ_MEDIA_IMAGES
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this ,
                        arrayOf(Manifest.permission.READ_MEDIA_IMAGES) ,
                        PERMISSION_REQUEST_CODE
                    )
                } else openGallery()
            } else {
                if (ContextCompat.checkSelfPermission(
                        this , Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this ,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE) ,
                        PERMISSION_REQUEST_CODE
                    )
                } else {
                    openGallery()
                }
            }
        }
    }

    private fun firebaseDataUpdate(
        imgUri: String? ,
        mainCategory: String? ,
        name: String ,
        selectedItem: String? ,
    ) {
        if (imgUri != null && name.isNotEmpty()) {
            println("DTATATA $selectedItem")
            updateData(imgUri , mainCategory , name , selectedItem)
        } else {
            Toast.makeText(this , "Please Fill Data." , Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateData(
        imgUri: String ,
        mainCategory: String? ,
        name: String ,
        selectedItem: String? ,
    ) {
        val data = mapOf(
            "url" to imgUri ,
            "category" to mainCategory ,
            "name" to name ,
            "subCategory" to selectedItem
        )

        timeStamp?.let { timeStamp ->
            Log.d("EditPage" , "Querying with timestamp: $timeStamp")

            myRef.orderByChild("timestamp").equalTo(timeStamp)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            for (dataSnapshot in snapshot.children) {
                                Log.d("EditPage" , "Found matching record: ${dataSnapshot.key}")
                                dataSnapshot.ref.updateChildren(data).addOnSuccessListener {
                                    Toast.makeText(
                                        this@EditPage ,
                                        "Document updated successfully" ,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    Log.d("DATAATATAT" , data.toString())
                                }.addOnFailureListener { e ->
                                    Toast.makeText(
                                        this@EditPage ,
                                        "Error updating document: ${e.message}" ,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            Log.d(
                                "EditPage" , "No record found with the given timestamp: $timeStamp"
                            )
                            Toast.makeText(
                                this@EditPage ,
                                "No record found with the given timestamp" ,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d("EditPage" , error.message)
                    }
                })
        } ?: run {
            Toast.makeText(this , "Timestamp is missing" , Toast.LENGTH_SHORT).show()
        }

    }

    private fun getAdapter(selectedItem: String) {
        val adapter = ArrayAdapter(
            this , android.R.layout.simple_spinner_dropdown_item , Utils.subCategory
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        _binding.txtsubCategory.adapter = adapter

        val spinnerPosition = adapter.getPosition(selectedItem)
        _binding.txtsubCategory.setSelection(spinnerPosition)
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
            val selectedImageUri: Uri? = data?.data
            if (selectedImageUri != null) {
                _binding.imgView.setImageURI(selectedImageUri)
                imgUri = selectedImageUri.toString()
            } else {
                Toast.makeText(this , "Failed to get image" , Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this , "Permission denied" , Toast.LENGTH_SHORT).show()
            }
        }
    }
}
