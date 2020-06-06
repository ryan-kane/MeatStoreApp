package com.example.meatstoreapp.BuildOrder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meatstoreapp.Category
import com.example.meatstoreapp.R
import kotlinx.android.synthetic.main.fragment_product_browse.*
import layout.ProductBrowseRecyclerViewAdapter
import java.io.IOException

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ProductBrowseFragment(
    private val category: Category? = null
) : Fragment() , ProductRequester.ProductRequesterResponse {

    private lateinit var productRequester: ProductRequester

    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var adapter: ProductBrowseRecyclerViewAdapter


    private val productList: ArrayList<Product> = ArrayList()
    private val lastVisibleItemPosition: Int get() = gridLayoutManager.findLastVisibleItemPosition()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_browse, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gridLayoutManager = GridLayoutManager(this.requireContext(), 2)
        product_browse_recycler_view.layoutManager = gridLayoutManager

        adapter = ProductBrowseRecyclerViewAdapter(productList)
        product_browse_recycler_view.adapter = adapter

        productRequester = ProductRequester(this, category)

        setOnScrollListener()
    }

    override fun onStart() {
        super.onStart()
        if(productList.size == 0) {
            //TODO Loading Animation
            requestProductPage()
        }
    }

    fun requestProductPage() {
        try {
            productRequester.getNextProductPage()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun receivedNewProduct(newProduct: Product) {
        requireActivity().runOnUiThread(Runnable {
            productList.add(newProduct)
            adapter.notifyItemInserted(productList.size-1)
        })
    }

    private fun setOnScrollListener() {
        product_browse_recycler_view.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = product_browse_recycler_view.layoutManager!!.itemCount
                if (totalItemCount == lastVisibleItemPosition + 1) {
                    requestProductPage()
                }
            }
        })
    }
}
