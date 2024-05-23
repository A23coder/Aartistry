package com.aadhya.aartistry.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.aadhya.aartistry.R
import com.aadhya.aartistry.data.modal.CategoryData
import com.bumptech.glide.Glide

class CategoryAdapter(
    private val dataList: List<CategoryData> ,
    private val context: Context ,

    ) : BaseAdapter() {
    private var layoutInflater: LayoutInflater? = null
    private lateinit var catTextView: TextView
    private lateinit var cateImageView: ImageView
    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int , convertView: View? , parent: ViewGroup?): View {
        var convertView = convertView
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.category_card_item , null)
        }

        cateImageView = convertView!!.findViewById(R.id.cat_img)
        catTextView = convertView.findViewById(R.id.cat_txt)

        catTextView.text = dataList[position].cat_title
        Glide.with(convertView)
            .load(dataList[position].cat_image)
            .override(600 , 500)
            .into(cateImageView)

        return convertView
    }
}