package com.aman.ecommerce.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aman.ecommerce.R
import com.aman.ecommerce.data.model.Products
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_single_item.view.*

class ProductAdapter(var results: List<Products.Product?>) :
    RecyclerView.Adapter<ProductAdapter.ProductVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVH {
        return ProductVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_single_item,
                parent,
                false)
        )
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: ProductVH, position: Int) {
        holder.bind(results[position]!!)
    }


    class ProductVH(private val view: View): RecyclerView.ViewHolder(view)  {
        fun bind(result: Products.Product) {
            view.tv_product_name.text = result.name!!
            view.tv_product_price.text = result.price!!
            view.tv_product_spacial_price.text = result.special!!
            Picasso.get().load(result.image!!).into(view.iv_product)
        }
    }
}
