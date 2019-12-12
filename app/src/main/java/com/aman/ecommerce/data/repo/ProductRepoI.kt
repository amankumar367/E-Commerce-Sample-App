package com.aman.ecommerce.data.repo

import com.aman.ecommerce.data.model.Products
import com.aman.ecommerce.persistant.AppDatabase
import io.reactivex.Single

interface ProductRepoI {
    fun getProductList(): Single<Products>
    fun getLocalProductList(database: AppDatabase): Single<List<Products.Product>>
}
