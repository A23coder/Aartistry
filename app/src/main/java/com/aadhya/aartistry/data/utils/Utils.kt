package com.aadhya.aartistry.data.utils

import com.aadhya.aartistry.R
import com.aadhya.aartistry.data.modal.HomeData

class Utils {
    companion object {
        val mainCategory = listOf(
            "Select Category" ,
            "Mehandi Design" ,
            "NailArt Design" ,
            "HairStyle Design" ,
            "Makeup Design" ,
        )

        val subCategory = listOf(
            "Select Subcategory" ,
            "Arabic Mehndi Design" ,
            "Bridal Mehndi Design" ,
            "Indian Mehndi Design" ,
            "Moroccan Mehndi Design" ,
            "Bold Mehndi Design" ,
            "Indo Mehndi Design" ,
            "Floral Mehndi Design" ,
            "Rose Mehndi Design" ,
            "African Mehndi Design" ,
            "Butterfly Mehndi Design" ,
            "Portrait Mehndi Design" ,
            "Hanging Mehndi Design" ,
            "Backhand Simple Mehndi Design" ,
        )

        val actualCategory = listOf(
            "Arabic mehndi design" ,
            "Bridal  mehndi design" ,
            "Indian Mehndi design" ,
            "Moroccan Mehndi design" ,
            "Bold Mehndi design" ,
            "Indo  mehndi design" ,
            "Floral  mehndi design" ,
            "Rose  mehndi design" ,
            "African   mehndi design" ,
            "Butterfly mehndi design" ,
            "Portrait   mehndi design" ,
            "Hanging  mehndi design" ,
            "Backhand Simple mehndi design" ,
        )
        val data_list = listOf(
            HomeData(R.drawable.newbg , "Mehandi Design") ,
            HomeData(R.drawable.nailart , "NailArt Design") ,
            HomeData(R.drawable.hairstyle , "HairStyle Design") ,
            HomeData(R.drawable.makeup , "Makeup Design") ,
        )
    }
}