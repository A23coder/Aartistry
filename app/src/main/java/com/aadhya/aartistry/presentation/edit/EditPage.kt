package com.aadhya.aartistry.presentation.edit

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.aadhya.aartistry.R
import com.aadhya.aartistry.data.utils.Utils
import com.aadhya.aartistry.databinding.ActivityEditPageBinding
import com.bumptech.glide.Glide

class EditPage : AppCompatActivity() {
    private val GALLERY_REQUEST_CODE = 123
    private val PERMISSION_REQUEST_CODE = 456
    private lateinit var _binding: ActivityEditPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityEditPageBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        _binding.btnUploadImage.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this ,
                    Manifest.permission.READ_EXTERNAL_STORAGE
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

        when (val image = intent.getSerializableExtra("image")) {
            is Uri -> {
                Glide.with(this).load(image).into(_binding.imgView)
            }

            is Int -> {
                _binding.imgView.setImageResource(image)
            }

            else -> {
                _binding.imgView.setImageResource(R.drawable.ic_upload)
            }
        }
        val selectedItem = intent.getStringExtra("subCategory")
        if (selectedItem != null) {
            getAdapter(selectedItem)
        }
        _binding.txtmainCategory.text = intent.getStringExtra("category")
        val name = intent.getStringExtra("name").toString()
        _binding.edtName.setText(name)
    }

    private fun getAdapter(selectedItem: String) {
        val adapter =
            ArrayAdapter(this , android.R.layout.simple_spinner_dropdown_item , Utils.subCategory)
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
            _binding.imgView.setImageURI(data?.data)
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