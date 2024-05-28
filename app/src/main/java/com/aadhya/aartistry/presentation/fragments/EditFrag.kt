package com.aadhya.aartistry.presentation.fragments

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aadhya.aartistry.adapter.EditAdapter
import com.aadhya.aartistry.data.utils.Utils
import com.aadhya.aartistry.databinding.LayoutEditFragmentBinding

class EditFrag : Fragment() {
    private lateinit var _binding: LayoutEditFragmentBinding
    private lateinit var recyclerView: RecyclerView
    var selectedCategory = ""
    var selectedSubCategory = ""
    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)

        getSpinnerAdapter()
        initListners()
    }

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? , savedInstanceState: Bundle? ,
    ): View {
        _binding = LayoutEditFragmentBinding.inflate(inflater , container , false)
        recyclerView = _binding.editRecyclerView

        _binding.btnSearch.setOnClickListener {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = EditAdapter(
                Utils.data_list , requireContext() , selectedSubCategory , selectedCategory
            )
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
                    Toast.makeText(requireContext() , selectedSubCategory , Toast.LENGTH_SHORT)
                        .show()
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }
}