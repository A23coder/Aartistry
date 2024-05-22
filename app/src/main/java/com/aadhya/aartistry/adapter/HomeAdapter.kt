package com.aadhya.aartistry.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.aadhya.aartistry.R
import com.aadhya.aartistry.data.modal.HomeData
import com.bumptech.glide.Glide

class HomeAdapter(
    private var data: List<HomeData> ,
    var context: Context ,
) : RecyclerView.Adapter<HomeAdapter.MyViewViewHolder>() {
    class MyViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardImage: ImageView = itemView.findViewById(R.id.card_image)
        val cardText: TextView = itemView.findViewById(R.id.card_text)
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

//        holder.cardText.text = item.title
//        holder.cardImage.setImageResource(item.image)
        holder.itemView.setOnClickListener {
            Toast.makeText(context , item.title , Toast.LENGTH_SHORT).show()
        }
        holder.cardText.text = item.title
        Glide.with(holder.itemView.context)
            .load(item.image)
            .override(600 , 400) // Resize the image to fit within 600x400 pixels
            .into(holder.cardImage)
    }
}
