package com.example.meatstoreapp

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meatstoreapp.BuildOrder.ItemViewFragment
import com.example.meatstoreapp.BuildOrder.Product
import com.example.meatstoreapp.BuildOrder.ProductBrowseFragment
import com.example.meatstoreapp.BuildOrder.ProductRequester
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_view_grid_item.view.*
import layout.ProductBrowseRecyclerViewAdapter

class CategoryBrowseRecyclerViewAdapter(
    private val fragManager: FragmentManager,
    private val categories: ArrayList<Category>): RecyclerView.Adapter<CategoryBrowseRecyclerViewAdapter.CategoryHolder>() {

    class CategoryHolder(v: View, val fm: FragmentManager) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view : View = v
        private var category : Category? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            // create a new product browse fragment for the category
            val productBrowse = ProductBrowseFragment(category)
            fm.beginTransaction()
                .replace(R.id.item_browse_container, productBrowse)
                .addToBackStack(null)
                .commit()
        }

        fun bindCategory(category: Category) {
            this.category = category
            val categoryThumbnail = category.image
            if(categoryThumbnail != null) {
                Picasso.with(view.context).load(categoryThumbnail.urlString).into(view.itemImage)
            }
            view.itemName.text = category.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val inflatedView = parent.inflate(R.layout.recycler_view_grid_item)
        return CategoryHolder(inflatedView, fragManager)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val itemCategory = categories[position]
        holder.bindCategory(itemCategory)
    }
}