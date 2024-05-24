package com.aadhya.aartistry.presentation.subcategory

import android.os.Bundle
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aadhya.aartistry.R
import com.aadhya.aartistry.adapter.SubCatAdapter
import com.aadhya.aartistry.data.modal.CategoryData
import com.aadhya.aartistry.databinding.ActivitySubCatBinding

class SubCat : AppCompatActivity() {
    private lateinit var binding: ActivitySubCatBinding
    private lateinit var gridView: GridView
    private lateinit var dataList: List<CategoryData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubCatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getToolbar()
        gridView = binding.gridViewSub
        dataList = listOf(
            CategoryData(R.drawable.newbg , "Mehandi1 ") ,
            CategoryData(R.drawable.mahendiv , "Radiant Blooms11 ") ,
            CategoryData(R.drawable.nailart , "Ethereal Elegance 1") ,
            CategoryData(R.drawable.nailvideo , "Mystic Charms 1") ,
            CategoryData(R.drawable.nailvideo , "Enchanting Vines 1") ,
            CategoryData(R.drawable.hairstyle , "Mehandi1 ") ,
            CategoryData(R.drawable.hairstylevideo , "Celestial Hues1 ") ,
            CategoryData(R.drawable.makeup , "Whimsical Patterns 1") ,
            CategoryData(R.drawable.makeupvideo , "Timeless Grace 1") ,
            CategoryData(R.drawable.mahendiv , "Joyful Mandalas1 ") ,
            CategoryData(R.drawable.nailart , "Serene Symmetry1 ") ,
            CategoryData(R.drawable.nailvideo , "Cultural Treasures 1") ,
            CategoryData(R.drawable.hairstyle , "HennaWhisper Artistry1 ") ,
            CategoryData(R.drawable.hairstylevideo , "Mehndi Magic Hub 1") ,
        )
        val adapter = SubCatAdapter(dataList , this)
        gridView.adapter = adapter

        gridView.onItemClickListener = AdapterView.OnItemClickListener { _ , _ , position , _ ->
            Toast.makeText(applicationContext , dataList[position].cat_title , Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun getToolbar() {
        val toolbarText = intent.getStringExtra("category").toString()
        binding.toolBarSubCategory.title = toolbarText
        binding.toolBarSubCategory.setTitleTextColor(getColor(R.color.black))
        binding.toolBarSubCategory.setNavigationIcon(getDrawable(R.drawable.ic_back))
        setSupportActionBar(binding.toolBarSubCategory)
        binding.toolBarSubCategory.setNavigationOnClickListener {
            finish()
        }
    }
}