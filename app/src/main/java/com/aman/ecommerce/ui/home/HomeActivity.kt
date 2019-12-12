package com.aman.ecommerce.ui.home

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.aman.ecommerce.R
import com.aman.ecommerce.data.model.Products
import com.aman.ecommerce.data.repo.ProductRepo
import com.aman.ecommerce.extention.gone
import com.aman.ecommerce.extention.visible
import com.aman.ecommerce.persistant.AppDatabase
import com.aman.ecommerce.ui.adapter.ProductAdapter
import com.aman.ecommerce.ui.cart.CartActivity
import kotlinx.android.synthetic.main.activity_home.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class HomeActivity : AppCompatActivity() {

    private val productRepo = ProductRepo()

    private lateinit var viewModel: HomeViewModel

    private lateinit var mDB: AppDatabase
    private lateinit var executor: Executor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        init()
        setObserver()
        loadProducts()
        onClicks()

    }

    private fun init() {
        Log.d(TAG, " >>> Initializing viewModel")

        mDB = AppDatabase.getInstance(this)!!
        executor = Executors.newSingleThreadExecutor()

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.setRepository(productRepo)
    }

    private fun setObserver() {
        viewModel.observableState.observe(this, Observer {
            updateView(it)
        })
    }

    private fun loadProducts() {
        viewModel.getProductList()
    }

    private fun onClicks() {
        btn_retry.setOnClickListener {
            loadProducts()
        }
    }

    private fun updateView(state: HomeState?) {
        when {
            state!!.loading -> showLoading()
            state.success -> setProductRecyclerView(state.data as Products)
            state.failure -> showError()
        }
    }

    private fun setProductRecyclerView(products: Products) {
        hideLoading()
        rv_product.visible()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            layout_home.setBackgroundColor(resources.getColor(android.R.color.white))
            supportActionBar?.show()
        }

        rv_product.layoutManager = LinearLayoutManager(this)
        rv_product.adapter = products.products?.let { ProductAdapter(it, mDB, executor) }

    }

    private fun showError() {
        rv_product.gone()
        groupRetry.visible()
        hideLoading()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            layout_home.setBackgroundColor(resources.getColor(R.color.error_bg))
            supportActionBar?.hide()
        }
    }

    private fun showLoading() {
        rv_product.gone()
        groupRetry.gone()
        progressbar.visible()
    }

    private fun hideLoading() {
        progressbar.gone()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_cart -> {
                CartActivity.start(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AppDatabase.destroyInstance()
    }

    companion object {
        const val TAG = "HomeActivity"

        fun start(context: Context) {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }
    }
}
