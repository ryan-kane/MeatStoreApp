package com.example.meatstoreapp.BuildOrder

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Response
import com.example.meatstoreapp.Category
import com.example.meatstoreapp.CategoryBrowseRecyclerViewAdapter
import com.example.meatstoreapp.R
import com.example.meatstoreapp.WCAPI
import kotlinx.android.synthetic.main.fragment_category_browse.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

val MAX_NUM_CATEGORIES = 50

class CategoryBrowseFragment: Fragment() {

    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var adapter: CategoryBrowseRecyclerViewAdapter

    private val categoryList = ArrayList<Category>()

    private val categoriesPath = "products/categories"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category_browse, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gridLayoutManager = GridLayoutManager(this.requireContext(), 2)
        category_browse_recycler_view.layoutManager = gridLayoutManager

        adapter = CategoryBrowseRecyclerViewAdapter(requireActivity().supportFragmentManager, categoryList)
        category_browse_recycler_view.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        if (categoryList.size == 0) {
            // populate the categories
            getCategories()
        }
    }


    private fun getCategories() {
        val wcapi = WCAPI(this.requireContext())
        val path = categoriesPath
        val urlBuilder = WCAPI.WCUrlBuilder(path)
        urlBuilder.perPage = MAX_NUM_CATEGORIES
        val url = urlBuilder.buildUrlString()
        wcapi.sendGETRequest(url, getCategoriesResponseListener, getCategoriesResponseErrorListener)
        Log.d("getCategories", "Request Sent")
    }


    private val getCategoriesResponseListener = Response.Listener<String> { response ->
        try {
            val categoryJSONList = JSONArray(response)
            for(i in 0 until categoryJSONList.length()) {
                val categoryObject = JSONObject(categoryJSONList[i].toString())
                val newCategory = Category(categoryObject)
                requireActivity().runOnUiThread(Runnable {
                    categoryList.add(newCategory)
                    adapter.notifyItemInserted(categoryList.size - 1)
                })
            }
        }catch (e: JSONException) {
            //TODO Handle Error
            Log.e("getCategoriesResponse", "JSON Error: $e")
        }

    }

    private val getCategoriesResponseErrorListener = Response.ErrorListener { error ->
        Log.e("getCategories", "Failed GET: $error")
    }

}