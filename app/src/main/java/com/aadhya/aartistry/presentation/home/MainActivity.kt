package com.aadhya.aartistry.presentation.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aadhya.aartistry.R
import com.aadhya.aartistry.adapter.HomeAdapter
import com.aadhya.aartistry.data.modal.HomeData
import com.aadhya.aartistry.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = binding.recyclerView
        navigationDrawer()
        val data_list = listOf(
            HomeData(R.drawable.newbg , "Mehandi Design" , false) ,
            HomeData(R.drawable.mahendiv , "Mehandi Video" , true) ,
            HomeData(R.drawable.nailart , "NailArt Design" , false) ,
            HomeData(R.drawable.nailvideo , "NailArt Video" , true) ,
            HomeData(R.drawable.hairstyle , "HairStyle Design" , false) ,
            HomeData(R.drawable.hairstylevideo , "HairStyle Video" , true) ,
            HomeData(R.drawable.makeup , "Makeup Design" , false) ,
            HomeData(R.drawable.makeupvideo , "Makeup Video" , true) ,
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = HomeAdapter(data_list , this)
    }

    @SuppressLint("InflateParams")
    private fun navigationDrawer() {
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this , binding.myDrawerLayout , R.string.nav_open , R.string.nav_close
        )
        setSupportActionBar(binding.toolbar)

        binding.myDrawerLayout.addDrawerListener(actionBarDrawerToggle)
        binding.navigationDrawer.setBackgroundColor(Color.WHITE)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val inflater = LayoutInflater.from(this)
        val customTitleView = inflater.inflate(R.layout.toolbar_title , null)
        val params = Toolbar.LayoutParams(
            Toolbar.LayoutParams.MATCH_PARENT , Toolbar.LayoutParams.WRAP_CONTENT
        )
        binding.toolbar.addView(customTitleView , params)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}