package com.aadhya.aartistry.presentation.admin.upload

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.aadhya.aartistry.R
import com.aadhya.aartistry.utils.Constants.REQUEST_IMAGE_PICK
import com.aadhya.aartistry.utils.Constants.REQUEST_PERMISSION
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class UploadImageActivity : AppCompatActivity() {

    var mDatabaseReference:DatabaseReference?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_upload_image)

//        checkStoragepermission()

        mDatabaseReference = FirebaseDatabase.getInstance().reference



        fetchImageByName("Mehndi")


//        selectImage()

    }


    private fun checkStoragepermission() {
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_PERMISSION)
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            if (imageUri != null) {
                Toast.makeText(this, "Sucess", Toast.LENGTH_SHORT).show()
                uploadImageToFirebaseStorage(imageUri)
            }
        }
    }
    private fun uploadImageToFirebaseStorage(imageUri: Uri) {
        val storageReference = FirebaseStorage.getInstance().reference
        val imageRef = storageReference.child("images/${UUID.randomUUID()}.jpg")

        imageRef.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()
                    saveImageDetailsToDatabase(downloadUrl)
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Upload failed: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
    private fun saveImageDetailsToDatabase(imageUrl: String) {
        val databaseReference = FirebaseDatabase.getInstance().reference
        val imageDetails = hashMapOf(
            "url" to imageUrl,
            "name" to "Mehndi",
            "description" to "Best design",
            "timestamp" to System.currentTimeMillis()
        )

        databaseReference.child("images").push().setValue(imageDetails)
            .addOnSuccessListener {
                Toast.makeText(this, "Image details saved successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to save image details: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun fetchImageByName(imageName: String) {
        mDatabaseReference!!.child("images").orderByChild("name").equalTo(imageName).get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val imageUrl = dataSnapshot.child("url").getValue(String::class.java)
                        val name = dataSnapshot.child("name").getValue(String::class.java)
                        if (imageUrl != null) {
                            loadImageIntoImageView(imageUrl,name)
                        }
                    }
                } else {
                    Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { exception ->
                Log.d("##FAILURE",exception.toString())
                Toast.makeText(this, "Failed to fetch image: ${exception.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun loadImageIntoImageView(imageUrl: String, name: String?) {
        val imageView: ImageView = findViewById(R.id.img_view)
        val text: TextView = findViewById(R.id.txt_label)
        text.setText(name)
        Glide.with(this)
            .load(imageUrl)
            .into(imageView)
    }





}