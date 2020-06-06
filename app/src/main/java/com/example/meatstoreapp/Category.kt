package com.example.meatstoreapp

import org.json.JSONObject

class Category(val categoryJSONObject: JSONObject) {
    val id = categoryJSONObject.getInt("id")
    val name: String? = categoryJSONObject.get("name").toString()
    var image: Image? = null
    init {
        val imageJSON = JSONObject(categoryJSONObject.get("image").toString())
        image = Image(imageJSON)
    }
}