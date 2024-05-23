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
            CategoryData(R.drawable.newbg , "Mehandi ") ,
            CategoryData(R.drawable.mahendiv , "Radiant Blooms ") ,
            CategoryData(R.drawable.nailart , "Ethereal Elegance ") ,
            CategoryData(R.drawable.nailvideo , "Mystic Charms ") ,
            CategoryData(R.drawable.nailvideo , "Enchanting Vines ") ,
            CategoryData(R.drawable.hairstyle , "Mehandi ") ,
            CategoryData(R.drawable.hairstylevideo , "Celestial Hues ") ,
            CategoryData(R.drawable.makeup , "Whimsical Patterns ") ,
            CategoryData(R.drawable.makeupvideo , "Timeless Grace ") ,
            CategoryData(R.drawable.mahendiv , "Joyful Mandalas ") ,
            CategoryData(R.drawable.nailart , "Serene Symmetry ") ,
            CategoryData(R.drawable.nailvideo , "Cultural Treasures ") ,
            CategoryData(R.drawable.hairstyle , "HennaWhisper Artistry ") ,
            CategoryData(R.drawable.hairstylevideo , "Mehndi Magic Hub ") ,
        )
        val adapter = CategoryAdapter(dataList , this)
        gridView.adapter = adapter

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