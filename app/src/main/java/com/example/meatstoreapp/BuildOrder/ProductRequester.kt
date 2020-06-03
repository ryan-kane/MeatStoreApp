package com.example.meatstoreapp.BuildOrder

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.example.meatstoreapp.Product

const val MAX_PRODUCTS = 15
const val DEFAULT_BATCH_SIZE = 6

class ProductRequester(listeningFragment: Fragment) {
    var numProductsLoaded = 0

    interface ProductRequesterResponse {
        fun receivedNewProduct(newProduct: Product)
    }

    private val responseListener: ProductRequesterResponse

    init {
        responseListener = listeningFragment as ProductRequesterResponse
    }

    fun getProductBatch(batchSize: Int = DEFAULT_BATCH_SIZE) {
        var currBatchCount = 0
        while (currBatchCount < batchSize) {
            getProduct()
            currBatchCount++
        }
    }

    fun getProduct(){
        if(numProductsLoaded == MAX_PRODUCTS) {
            return
        }
        val url = "https://koenig-media.raywenderlich.com/uploads/2017/09/RecyclerView-feature.png"
        val productName = "Product Blah"
        responseListener.receivedNewProduct(Product(url, productName))
        numProductsLoaded++
    }
}