package com.aadhya.aartistry.presentation.subcategory

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aadhya.aartistry.R
import com.aadhya.aartistry.adapter.MehandiItemAdapter
import com.aadhya.aartistry.data.modal.MehandiItem
import com.aadhya.aartistry.databinding.ActivitySubCatBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SubCat : AppCompatActivity() {
    private lateinit var binding: ActivitySubCatBinding
    private lateinit var recyclerView: RecyclerView
    private val itemList = mutableListOf<MehandiItem>()
    private lateinit var adapter: MehandiItemAdapter

    var categoryname = ""
    var sCategoryname = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubCatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getToolbar()
        recyclerView = binding.gridViewSub
        recyclerView.layoutManager = GridLayoutManager(this , 2)
        adapter = MehandiItemAdapter(this , itemList)
        recyclerView.adapter = adapter
        categoryname = intent.extras!!.getString("category" , "")
        sCategoryname = intent.extras!!.getString("category" , "")
        getFirebaseDataList()
    }

    private fun getFirebaseDataList() {
//        val myRef = FirebaseDatabase.getInstance().getReference("images").child("subCategory").equals(categoryname)
        val myRef = FirebaseDatabase.getInstance().getReference("images")


        myRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                itemList.clear()
                for (dataSnapshot in snapshot.children) {
                    val item = dataSnapshot.getValue(MehandiItem::class.java)
                    item?.let {
                        if (it.subCategory?.trim() == categoryname.trim()) {
                            itemList.add(it)
//                            println("====222" + itemList)
                        } else if (it.category?.trim() == sCategoryname) {
                            itemList.add(it)
                        } else {
                            // TODO:
                        }
                    }
                }
                adapter.notifyDataSetChanged()
                Log.d("FirebaseData" , "Items: $itemList")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseError" , "Error: ${error.message}")
            }
        })
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
