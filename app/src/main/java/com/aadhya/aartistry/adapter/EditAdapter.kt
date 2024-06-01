package com.aadhya.aartistry.adapter

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.aadhya.aartistry.R
import com.aadhya.aartistry.data.modal.MehandiItem
import com.aadhya.aartistry.presentation.edit.EditPage
import com.bumptech.glide.Glide


class EditAdapter(
    private var data: List<MehandiItem> ,
    var context: Context ,
) : RecyclerView.Adapter<EditAdapter.MyViewViewHolder>() {
    class MyViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardImage: ImageView = itemView.findViewById(R.id.catImage)
        val cardName: TextView = itemView.findViewById(R.id.txt_name)
        val cateEdit: ImageView = itemView.findViewById(R.id.icEdit)
        val cateDelete: ImageView = itemView.findViewById(R.id.icDelete)

    }


    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): MyViewViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.edit_items , parent , false)
        return MyViewViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewViewHolder , position: Int) {
        val item = data[position]
        holder.cardName.text = item.name
        Glide.with(holder.itemView)
            .load(item.url)
            .into(holder.cardImage)



        holder.cateEdit.setOnClickListener {
            println("timestamp.... ${item.timestamp.toString()}")
            val intent = Intent(context , EditPage::class.java).apply {
                putExtra("name" , item.name)
                putExtra("image" , item.url)
                putExtra("subCategory" , item.subCategory)
                putExtra("category" , item.category)
                putExtra("time" , item.timestamp.toString())
            }
            context.startActivity(intent)
        }
        holder.cateDelete.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Are you sure you want to Delete this ${item.name}?")
            builder.setTitle("Delete Item!!")
            builder.setCancelable(true)

            builder.setPositiveButton("Yes" , object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface? , which: Int) {
                    Toast.makeText(context , "Sure......" , Toast.LENGTH_SHORT).show()
                }
            })
            builder.setNegativeButton("No" , object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface? , which: Int) {
                    dialog?.cancel()
                }
            })
            val alertDialog = builder.create()
            alertDialog.show()
        }
//        holder.itemView.setOnClickListener {
//            val intent = Intent(context , EditPage::class.java).apply {
//                  putExtra("category" , holder.cardText.text)
//            }
//            context.startActivity(intent)
//        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}