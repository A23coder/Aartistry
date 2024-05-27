package com.aadhya.aartistry.presentation.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.aadhya.aartistry.R
import com.aadhya.aartistry.data.utils.Utils
import com.aadhya.aartistry.databinding.LayoutAddFragmentBinding

class AddFrag : Fragment() {
    private lateinit var _binding: LayoutAddFragmentBinding
    private val GALLERY_REQUEST_CODE = 1000

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)

        getSpinnerAdapter()
    }

    private fun getSpinnerAdapter() {
        val subCateAdapter = ArrayAdapter(
            requireContext() ,
            android.R.layout.simple_spinner_dropdown_item ,
            Utils.subCategory
        )
        _binding.subCategory.adapter = subCateAdapter
        _binding.subCategory.isEnabled = true

        val mainCateAdapter = ArrayAdapter(
            requireContext() ,
            android.R.layout.simple_spinner_dropdown_item ,
            Utils.mainCategory
        )
        _binding.mainCategory.adapter = mainCateAdapter
        _binding.mainCategory.isEnabled = true
    }

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? , savedInstanceState: Bundle? ,
    ): View {
        _binding = LayoutAddFragmentBinding.inflate(inflater , container , false)

        _binding.btnAddImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            _binding.imgAddView.setImageURI(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

            intent.type = "image/*"
            startActivityForResult(intent , GALLERY_REQUEST_CODE)
            _binding.imgAddView.setImageResource(R.drawable.ic_upload)
        }

        return _binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int , resultCode: Int , data: Intent?) {
        super.onActivityResult(requestCode , resultCode , data)
        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            _binding.imgAddView.setImageURI(data?.data)
        }
    }
}