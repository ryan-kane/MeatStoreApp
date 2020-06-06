package com.example.meatstoreapp.BuildOrder

import android.util.Log
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.meatstoreapp.BackgroundThread
import com.example.meatstoreapp.Category
import com.example.meatstoreapp.WCAPI
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URL
import java.util.concurrent.Executor

const val MAX_PRODUCTS = 15
const val DEFAULT_PAGE_SIZE = 50

class ProductRequester(
    listeningFragment: Fragment,
    private var category: Category? = null){
    var numPagesLoaded = 0
    var nextProductPage = 1

    private val wcapi = WCAPI(listeningFragment.requireContext())

    // Data Structures
    val productIdList: ArrayList<Int> = ArrayList()
    val productMap : HashMap<Int, Product> = HashMap()
    val categoryMap: HashMap<String, HashSet<Int>> = HashMap()

    // paths
    private val productPath = "products"


    interface ProductRequesterResponse {
        fun receivedNewProduct(newProduct: Product)
    }

    private val responseListener: ProductRequesterResponse

    init {
        responseListener = listeningFragment as ProductRequesterResponse
    }

    fun getNextProductPage() {
        Log.d("getNextProductPage", "getting product page requested: Page $nextProductPage")
        getProductPage(nextProductPage)
        nextProductPage++
    }

    private fun getProductPage(pageNumber: Int) {
        val path = productPath
        val urlBuilder = WCAPI.WCUrlBuilder(path)
        urlBuilder.pageNum = pageNumber
        urlBuilder.perPage = DEFAULT_PAGE_SIZE
        urlBuilder.category = category
        val url = urlBuilder.buildUrlString()
        wcapi.sendGETRequest(url, getProductsResponseListener, getProductsResponseErrorListener)
    }

    fun getProduct(productId: Int){
        if(productMap.containsKey(productId)) {
            // send the product via interface
            responseListener.receivedNewProduct(productMap[productId]!!)
        }
        //TODO query for product id from store
    }

    private val getProductsResponseListener = Response.Listener<String> { response ->
        try {
            val productList = JSONArray(response)
            if (productList.length() == 0) {
                return@Listener
            }
            for (i in 0 until productList.length()) {
                val productObject = JSONObject(productList[i].toString())
                val productId = productObject.getInt("id")
                productIdList.add(productId)
                val newProduct = Product(productObject)
                responseListener.receivedNewProduct(newProduct)
                productMap[productId] = newProduct
            }
        }catch (e: JSONException) {
            //TODO Handle Error
            Log.e("getProductsResponse", "JSON Error: $e")
        }

        Log.d("getProducts", "Products Gotten")
    }

    private val getProductsResponseErrorListener = Response.ErrorListener { error ->
        Log.e("sendGETRequest", "Failed GET: $error")
        //TODO Handle Error
    }


//    fun getNextProduct() {
//        /**
//         * Method for retrieving products from the ProductStore Asynchronously
//         */
//        if(productStore.productIdList.size > nextProduct){
//            val productId = productStore.productIdList[nextProduct]
//            val product = productStore.getProduct(productId)
//            if (product != null) {
//                responseListener.receivedNewProduct(
//                    product
//                )
//                nextProduct++
//            }
//        }
//    }


}