package com.aadhya.aartistry.data.utils

import com.aadhya.aartistry.R
import com.aadhya.aartistry.data.modal.HomeData

class Utils {
    companion object {
        val mainCategory = listOf(
            "Select Category",
            "Mehandi Design" ,
            "Mehandi Video" ,
            "NailArt Design" ,
            "NailArt Video" ,
            "HairStyle Design" ,
            "HairStyle Video" ,
            "Makeup Design" ,
            "Makeup Video" ,
        )

        val subCategory = listOf(
            "Select Subcategory",
            "Arabic mehndi design" ,
            "Bridal  mehndi design" ,
            "Indian  mehndi design" ,
            "Moroccan  mehndi design" ,
            "Bold  mehndi design" ,
            "Indo  mehndi design" ,
            "Floral  mehndi design" ,
            "Rose  mehndi design" ,
            "African   mehndi design" ,
            "Butterfly mehndi design" ,
            "Portrait   mehndi design" ,
            "Hanging  mehndi design" ,
            "Backhand Simple mehndi design" ,
        )

        val actualCategory = listOf(
            "Arabic mehndi design" ,
            "Bridal  mehndi design" ,
            "Indian  mehndi design" ,
            "Moroccan  mehndi design" ,
            "Bold  mehndi design" ,
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
            HomeData(R.drawable.newbg , "Mehandi Design" , false) ,
            HomeData(R.drawable.mahendiv , "Mehandi Video" , true) ,
            HomeData(R.drawable.nailart , "NailArt Design" , false) ,
            HomeData(R.drawable.nailvideo , "NailArt Video" , true) ,
            HomeData(R.drawable.hairstyle , "HairStyle Design" , false) ,
            HomeData(R.drawable.hairstylevideo , "HairStyle Video" , true) ,
            HomeData(R.drawable.makeup , "Makeup Design" , false) ,
            HomeData(R.drawable.makeupvideo , "Makeup Video" , true) ,
        )
    }
}