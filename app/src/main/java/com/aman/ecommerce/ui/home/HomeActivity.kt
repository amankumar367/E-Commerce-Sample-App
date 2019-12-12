package com.aman.ecommerce.ui.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.aman.ecommerce.R
import com.aman.ecommerce.data.repo.ProductRepo

class HomeActivity : AppCompatActivity() {

    private val productRepo = ProductRepo()

    private lateinit var viewModel: HomeViewModel

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

    }

    private fun updateView(state: HomeState?) {
        when {
            state!!.loading -> {}
            state.success -> {}
            state.failure -> {}
        }
    }

    companion object {
        const val TAG = "HomeActivity"

        fun start(context: Context) {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }
    }
}
