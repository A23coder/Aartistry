package com.aadhya.aartistry.presentation.categories

import android.os.Bundle
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aadhya.aartistry.R
import com.aadhya.aartistry.adapter.CategoryAdapter
import com.aadhya.aartistry.data.modal.CategoryData
import com.aadhya.aartistry.databinding.ActivitySubcategoryBinding

class Subcategory : AppCompatActivity() {
    private lateinit var gridView: GridView
    private lateinit var dataList: List<CategoryData>
    private lateinit var binding: ActivitySubcategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubcategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getToolbar()
        gridView = binding.gridView
        dataList = listOf(
            CategoryData(R.drawable.newbg , "Arabic Mehndi Design") ,
            CategoryData(R.drawable.mahendiv , "Bridal Mehndi Design") ,
            CategoryData(R.drawable.nailart , "Indian Mehndi Design") ,
            CategoryData(R.drawable.nailvideo , "Moroccan Mehndi Design") ,
            CategoryData(R.drawable.hairstyle , "Bold Mehndi Design") ,
            CategoryData(R.drawable.hairstylevideo , "Indo Mehndi Design") ,
            CategoryData(R.drawable.makeup , "Floral Mehndi Design") ,
            CategoryData(R.drawable.makeupvideo , "Rose Mehndi Design") ,
            CategoryData(R.drawable.newbg , "African Mehndi Design") ,
            CategoryData(R.drawable.mahendiv , "Butterfly Mehndi Design") ,
            CategoryData(R.drawable.nailart , "Portrait Mehndi Design") ,
            CategoryData(R.drawable.hairstyle , "Backhand Simple Mehndi Design") ,
            CategoryData(R.drawable.hairstylevideo , "Hanging Mehndi Design") ,
        )
        val adapter = CategoryAdapter(dataList , this)
        gridView.adapter = adapter
//        getFirebaseDataList()
        gridView.onItemClickListener = AdapterView.OnItemClickListener { _ , _ , position , _ ->
            Toast.makeText(applicationContext , dataList[position].cat_title , Toast.LENGTH_SHORT)
                .show()
        }
    }


    private fun getToolbar() {
        val toolbarText = intent.getStringExtra("category").toString()
        binding.toolBarCategory.title = toolbarText
        binding.toolBarCategory.setTitleTextColor(getColor(R.color.black))
        binding.toolBarCategory.setNavigationIcon(getDrawable(R.drawable.ic_back))
        setSupportActionBar(binding.toolBarCategory)
        binding.toolBarCategory.setNavigationOnClickListener {
            finish()
        }
    }
}