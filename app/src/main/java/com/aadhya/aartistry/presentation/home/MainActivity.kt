package com.aadhya.aartistry.presentation.home

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.aadhya.aartistry.R
import com.aadhya.aartistry.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigationDrawer()

    }

    private fun navigationDrawer() {
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this , binding.myDrawerLayout , R.string.nav_open , R.string.nav_close
        )
        setSupportActionBar(binding.toolbar)

        binding.myDrawerLayout.addDrawerListener(actionBarDrawerToggle)
        binding.navigationDrawer.setBackgroundColor(Color.LTGRAY)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}