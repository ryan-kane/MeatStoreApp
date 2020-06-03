package layout

import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.meatstoreapp.Product
import com.example.meatstoreapp.R
import com.example.meatstoreapp.inflate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_view_grid_item.view.*

class ItemBrowseRecyclerViewAdapter(private val products: ArrayList<Product>): RecyclerView.Adapter<ItemBrowseRecyclerViewAdapter.ProductHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val inflatedView = parent.inflate(R.layout.recycler_view_grid_item)
        return ProductHolder(inflatedView)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ItemBrowseRecyclerViewAdapter.ProductHolder, position: Int) {
        val itemProduct = products[position]
        holder.bindProduct(itemProduct)
    }

    class ProductHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view : View = v
        private var product : Product? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Log.d("RecyclerView", "CLICK" + this.product?.productName)

        }

        fun bindProduct(product: Product) {
            this.product = product
            Picasso.with(view.context).load(product.photoUrl).into(view.itemImage)
            view.itemName.text = product.productName
        }

        companion object {
            private val PHOTO_KEY = "PHOTO"
        }
    }
}

