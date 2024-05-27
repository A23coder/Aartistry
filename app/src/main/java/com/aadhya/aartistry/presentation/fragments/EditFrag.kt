package com.aadhya.aartistry.presentation.fragments

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aadhya.aartistry.adapter.EditAdapter
import com.aadhya.aartistry.data.utils.Utils
import com.aadhya.aartistry.databinding.LayoutEditFragmentBinding

class EditFrag : Fragment() {
    private lateinit var _binding: LayoutEditFragmentBinding
    private lateinit var recyclerView: RecyclerView
    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)

        getSpinnerAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? , savedInstanceState: Bundle? ,
    ): View {
        _binding = LayoutEditFragmentBinding.inflate(inflater , container , false)
        recyclerView = _binding.editRecyclerView


        _binding.btnUpdate.setOnClickListener {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = EditAdapter(Utils.data_list , requireContext())
        }

        return _binding.root
    }

    private fun getSpinnerAdapter() {
        val subCateAdapter = ArrayAdapter(
            requireContext() , R.layout.simple_spinner_dropdown_item , Utils.subCategory
        )
        _binding.subCategory.adapter = subCateAdapter
        _binding.subCategory.isEnabled = true

        val mainCateAdapter = ArrayAdapter(
            requireContext() , R.layout.simple_spinner_dropdown_item , Utils.mainCategory
        )
        _binding.mainCategory.adapter = mainCateAdapter
        _binding.mainCategory.isEnabled = true

    }

}