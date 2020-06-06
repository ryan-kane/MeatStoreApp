package com.example.meatstoreapp.BuildOrder

import com.example.meatstoreapp.Image
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class Product(productJSON: JSONObject) {
    val id = productJSON.getInt("id")
    val name = productJSON.get("name").toString()
    val imageList = ArrayList<Image>()

    init {
        val imageListJSON = JSONArray(productJSON.get("images").toString())
        for (i in 0 until imageListJSON.length()) {
            val imageJSON = JSONObject(imageListJSON[i].toString())
            val image = Image(imageJSON)
            imageList.add(image)
        }
    }

    fun getThumbnailImage(): Image? {
        if (imageList.size == 0){
            return null
        }else{
            return imageList[0]
        }
    }
}