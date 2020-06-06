package com.example.meatstoreapp

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.net.URL

class WCAPI(val context: Context) {

    fun sendGETRequest(
        url: String,
        responseListener: Response.Listener<String>,
        responseErrorListener: Response.ErrorListener
    ) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            responseListener,
            responseErrorListener
        )
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    class WCUrlBuilder(val path: String) {

        //  API DOCS:
        //  https://woocommerce.github.io/woocommerce-rest-api-docs/#retrieve-a-product
        private val webUrl = "https://bearbrookgamemeats.com/wp-json/wc/v3/"

        // authentication
        private val consumerKey: String = com.example.meatstoreapp.BuildConfig.CONSUMER_KEY
        private val consumerSecret: String =
            com.example.meatstoreapp.BuildConfig.CONSUMER_SECRET

        var perPage: Int? = null
        var pageNum: Int? = null
        var category: Category? = null

        fun buildUrlString(): String {
            // example
            // https://bearbrookgamemeats.com/wp-json/wc/v3/products/categories?consumer_key=ck_9240b1f2df677dfc50cb49556b00653685d9bd0e&consumer_secret=cs_33a99d70726ef6f47ad6ea8a269910a17b173992

            // build path
            val url: StringBuilder = StringBuilder(webUrl)
            url.append(path)

            // add product id
            // TODO

            // add parameters
            url.append('?')
            // per_page
            if(perPage != null) {
                url.append("per_page=${perPage.toString()}")
                url.append('&')
            }
            // page
            if(pageNum != null) {
                url.append("page=${pageNum.toString()}")
                url.append('&')
            }
            // category
            if (category != null) {
                url.append("category=${category!!.id}")
                url.append('&')
            }

            // add authorization

            url.append("consumer_key=$consumerKey")
            url.append('&')
            url.append("consumer_secret=$consumerSecret")
            return url.toString()
        }

        fun buildUrl(): URL {
            val urlString = buildUrlString()
            return URL(urlString)
        }
    }
}