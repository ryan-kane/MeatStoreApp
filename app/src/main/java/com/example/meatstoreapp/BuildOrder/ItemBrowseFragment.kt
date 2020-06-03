package com.example.meatstoreapp.BuildOrder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meatstoreapp.Product
import com.example.meatstoreapp.R
import kotlinx.android.synthetic.main.fragment_item_browse.*
import layout.ItemBrowseRecyclerViewAdapter
import java.io.IOException

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ItemBrowseFragment : Fragment() , ProductRequester.ProductRequesterResponse {
    private lateinit var productRequester: ProductRequester
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var adapter: ItemBrowseRecyclerViewAdapter
    private val productList: ArrayList<Product> = ArrayList()
    private val lastVisibleItemPosition: Int get() = gridLayoutManager.findLastVisibleItemPosition()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_browse, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gridLayoutManager = GridLayoutManager(this.requireContext(), 2)
        item_browse_recycler_view.layoutManager = gridLayoutManager

        adapter = ItemBrowseRecyclerViewAdapter(productList)
        item_browse_recycler_view.adapter = adapter

        productRequester = ProductRequester(this)

        setOnScrollListener()

    }

    override fun onStart() {
        super.onStart()
        if(productList.size == 0) {
            requestProductBatch()
        }
    }

    fun requestProductBatch() {
        try {
            productRequester.getProductBatch()
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
        item_browse_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = item_browse_recycler_view.layoutManager!!.itemCount
                if(totalItemCount == lastVisibleItemPosition + 1) {
                    requestProductBatch()
                }
            }
        })
    }

}
