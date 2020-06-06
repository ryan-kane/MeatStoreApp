package com.example.meatstoreapp.BuildOrder

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.meatstoreapp.BuildConfig
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.net.URL

class ProductsStore(// App Context
    var appContext: Context,
    var listener: ProductStoreResponse
) {

    interface ProductStoreResponse {
        fun productStoreReady()
    }

    // Data Structures
    var productIdList: ArrayList<Int> = ArrayList()
    var productMap : HashMap<Int, Product> = HashMap()
    var categoryMap: HashMap<String, HashSet<Int>> = HashMap()

    // authentication
    private val consumerKey: String = BuildConfig.CONSUMER_KEY
    private val consumerSecret: String =
        BuildConfig.CONSUMER_SECRET

    // paths
    private val webUrl = "https://bearbrookgamemeats.com/wp-json/wc/v3/"
    private val productPath = "products"
    private val categoriesPath = "products/categories"

    fun getProducts() {
        // products will be tied to their id
        val path = productPath
        sendGETRequest(path, getProductsResponseListener, getProductsResponseErrorListener)
        Log.d("getProductsRespList", "done")
    }

    fun getProduct(productId: Int): Product?{
        if(productMap.containsKey(productId)) {
            return productMap[productId]
        }
        return null
    }

    private val getProductsResponseListener = Response.Listener<String> { response ->
        val productList = JSONArray(response)
        for (i in 0 until productList.length()) {
            val productObject = JSONObject(productList[i].toString())
            val productId = productObject.getInt("id")
            productIdList.add(productId)
            val newProduct = Product(productObject)
            productMap[productId] = newProduct
            val categoryList = JSONArray(productObject.get("categories").toString())
            for(j in 0 until categoryList.length()-1) {
                val categoryObject = JSONObject(categoryList[i].toString())
                val categoryName = categoryObject.get("name").toString()
                if (categoryMap.containsKey(categoryName)){
                    categoryMap[categoryName]!!.add(productId)
                } else {
                    val hashSet = HashSet<Int>()
                    hashSet.add(productId)
                    categoryMap[categoryName] = hashSet
                }
            }
        }
        Log.d("getProducts", "Store Populated")
        listener.productStoreReady()
    }

    private val getProductsResponseErrorListener = Response.ErrorListener { error ->
        Log.e("sendGETRequest", "Failed GET: $error")
    }

    fun getCategories() {
        //TODO: finish this function

    }

    private fun buildUrlString(path: String): String {
        // example
        // https://bearbrookgamemeats.com/wp-json/wc/v3/products/categories?consumer_key=ck_9240b1f2df677dfc50cb49556b00653685d9bd0e&consumer_secret=cs_33a99d70726ef6f47ad6ea8a269910a17b173992

        // build path
        val url: StringBuilder = StringBuilder(webUrl)
        url.append(path)

        // add authorization
        url.append('?')
        url.append("consumer_key=$consumerKey")
        url.append('&')
        url.append("consumer_secret=$consumerSecret")

        return url.toString()
    }

    private fun buildUrl(path: String): URL {
        val urlString = buildUrlString(path)
        return URL(urlString)
    }

    private fun sendGETRequest(
        path: String,
        responseListener: Response.Listener<String>,
        responseErrorListener: Response.ErrorListener
    ) {
        val url = buildUrlString(path)
        var responseJson: JSONObject? = null
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this.appContext)

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            responseListener,
            responseErrorListener
        )
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }


    //TODO add tags???

}