package com.aman.ecommerce.data.repo

import android.util.Log
import com.aman.ecommerce.EcomApplication
import com.aman.ecommerce.data.model.Products
import com.aman.ecommerce.network.ApiInterface
import com.aman.ecommerce.persistant.AppDatabase
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Exception
import javax.inject.Inject

class ProductRepo: ProductRepoI {

    @Inject
    lateinit var retrofit: Retrofit

    private var productApi: ApiInterface? = null

    init {
        EcomApplication.getAppComponent()!!.inject(this)
        productApi = retrofit.create(ApiInterface::class.java)
    }

    override fun getProductList(): Single<Products> {
        return Single.create<Products> { emitter ->

            productApi!!.getProductList().enqueue(object : Callback<Products> {

                override fun onResponse(
                    call: Call<Products>,
                    response: Response<Products>
                ) {
                    Log.d(TAG, " >>> Hitting url : ${call.request().url()}")

                    response.body()?.let {
                        emitter.onSuccess(it)
                    }
                }

                override fun onFailure(call: Call<Products>, t: Throwable) {
                    Log.d(TAG, " >>> Hitting url : ${call.request().url()}")

                    emitter.onError(t)
                }
            })
        }
    }

    override fun getLocalProductList(database: AppDatabase): Single<List<Products.Product>> {
        return Single.create<List<Products.Product>> { emitter ->
            database.productDao().getAllProducts()?.let {
                emitter.onSuccess(it)
            } ?.run {
                emitter.onSuccess(listOf())
            }
        }
    }

    companion object {
        const val TAG = "ProductRepo"
    }
}