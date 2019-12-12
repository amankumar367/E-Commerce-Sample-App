package com.aman.ecommerce.network

import com.aman.ecommerce.data.model.Products
import com.aman.ecommerce.utils.PRODUCT_LIST_URL
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET(PRODUCT_LIST_URL)
    fun getProductList(): Call<Products>
}