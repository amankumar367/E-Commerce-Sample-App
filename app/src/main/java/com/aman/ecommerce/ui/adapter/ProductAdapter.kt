package com.aman.ecommerce.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.aman.ecommerce.R
import com.aman.ecommerce.data.model.Products
import com.aman.ecommerce.extention.gone
import com.aman.ecommerce.extention.visible
import com.aman.ecommerce.persistant.AppDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_single_item.view.*
import java.util.concurrent.Executor

class ProductAdapter(
    private val results: List<Products.Product?>,
    private val appDatabase: AppDatabase,
    private val executor: Executor
) :
    RecyclerView.Adapter<ProductAdapter.ProductVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVH {
        return ProductVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_single_item,
                parent,
                false), appDatabase, executor
        )
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: ProductVH, position: Int) {
        holder.bind(results[position]!!)
        holder.setIsRecyclable(false)
    }


    class ProductVH(private val view: View,
                    private val db: AppDatabase,
                    private val executor: Executor): RecyclerView.ViewHolder(view)  {
        fun bind(result: Products.Product) {
            view.tv_product_name.text = result.name!!
            view.tv_product_price.text = result.price!!
            view.tv_product_spacial_price.text = result.special!!
            Picasso.get().load(result.image!!).into(view.iv_product)

            executor.execute {
                db.productDao().getProduct(result.id)?.let {
                    (view.context as AppCompatActivity).runOnUiThread {
                        hideAddButton()
                        updateView(it.quantity!!)
                        onClicks(it)
                    }

                } ?: run {
                    (view.context as AppCompatActivity).runOnUiThread {
                        onClicks(result)
                    }
                }
            }
        }

        private fun onClicks(result: Products.Product) {
            view.btn_add.setOnClickListener {
                executor.execute {
                    db.productDao().insertProduct(result)
                    (view.context as AppCompatActivity).runOnUiThread {
                        hideAddButton()
                        updateView(result.quantity!!)
                    }
                }
            }

            view.btn_plus.setOnClickListener {
                executor.execute {
                    when {
                        result.quantity!! > 0 -> {
                            result.quantity = result.quantity!!.inc()
                            db.productDao().updateProduct(result)
                            (view.context as AppCompatActivity).runOnUiThread {
                                updateView(result.quantity!!)
                            }
                        }
                    }
                }
            }

            view.btn_minus.setOnClickListener {
                executor.execute {
                    if (result.quantity!! > 0) {

                        if (result.quantity!! == 1) {
                            db.productDao().deleteProduct(result)
                            (view.context as AppCompatActivity).runOnUiThread {
                                updateView(result.quantity!!)
                                showAddButton()
                            }
                        } else {
                            result.quantity = result.quantity!!.dec()
                            db.productDao().updateProduct(result)
                            (view.context as AppCompatActivity).runOnUiThread {
                                updateView(result.quantity!!)
                            }
                        }
                    }
                }
            }
        }

        private fun updateView(quantity: Int) {
            view.tv_item_count.text = quantity.toString()
        }

        private fun showAddButton() {
            view.btn_add.visible()
            view.groupAddToCart.gone()
        }

        private fun hideAddButton() {
            view.btn_add.gone()
            view.groupAddToCart.visible()
        }
    }
}
