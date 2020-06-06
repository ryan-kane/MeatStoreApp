package com.example.meatstoreapp

import org.json.JSONObject

class Image(imageJSONObject: JSONObject) {
    val urlString = imageJSONObject.get("src").toString()
}