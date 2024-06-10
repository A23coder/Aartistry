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
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds

class Subcategory : AppCompatActivity() {
    private lateinit var gridView: GridView
    private lateinit var dataList: List<CategoryData>
    private lateinit var binding: ActivitySubcategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubcategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getToolbar()
        loadBanner()
        gridView = binding.gridView
        dataList = listOf(
            CategoryData(R.drawable.arabic , "Arabic Mehndi Design") ,
            CategoryData(R.drawable.bridal , "Bridal Mehndi Design") ,
            CategoryData(R.drawable.indian , "Indian Mehndi Design") ,
            CategoryData(R.drawable.morrocon , "Moroccan Mehndi Design") ,
            CategoryData(R.drawable.bold , "Bold Mehndi Design") ,
            CategoryData(R.drawable.indoarabic , "Indo Mehndi Design") ,
            CategoryData(R.drawable.floaral , "Floral Mehndi Design") ,
            CategoryData(R.drawable.rose , "Rose Mehndi Design") ,
            CategoryData(R.drawable.african , "African Mehndi Design") ,
            CategoryData(R.drawable.butterfly , "Butterfly Mehndi Design") ,
            CategoryData(R.drawable.potrait , "Portrait Mehndi Design") ,
            CategoryData(R.drawable.backhand , "Backhand Simple Mehndi Design") ,
            CategoryData(R.drawable.hanging , "Hanging Mehndi Design") ,
            CategoryData(R.drawable.babyshower , "BabyShower Mehndi Design") ,
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

    //    ca-app-pub-6786932636090048/6765687813
    private fun loadBanner() {
        MobileAds.initialize(this)

        val adRequest = AdRequest.Builder().build()

        binding.adView.loadAd(adRequest)
    }
}