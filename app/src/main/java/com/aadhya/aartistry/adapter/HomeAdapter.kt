package com.aadhya.aartistry.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aadhya.aartistry.R
import com.aadhya.aartistry.data.modal.HomeData
import com.aadhya.aartistry.presentation.categories.Subcategory
import com.bumptech.glide.Glide

class HomeAdapter(
    private var data: List<HomeData> ,
    var context: Context ,
) : RecyclerView.Adapter<HomeAdapter.MyViewViewHolder>() {
    class MyViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardImage: ImageView = itemView.findViewById(R.id.card_image)
        val cardText: TextView = itemView.findViewById(R.id.card_text)
        val cardVideo: ImageView = itemView.findViewById(R.id.ic_video)
    }


    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): MyViewViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.home_list_item , parent , false)
        return MyViewViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewViewHolder , position: Int) {
        val item = data[position]
        holder.cardText.text = item.title
        when (item.isVideo) {
            true -> {
                holder.cardVideo.visibility = View.VISIBLE
            }
            false -> {
                holder.cardVideo.visibility = View.GONE
            }
        }
        Glide.with(holder.itemView)
            .load(item.image)
            .override(600 , 400)
            .into(holder.cardImage)
        holder.itemView.setOnClickListener {
            val intent = Intent(context , Subcategory::class.java).apply {
                putExtra("category" , holder.cardText.text)
            }
            context.startActivity(intent)
        }
    }
}
