package com.aman.ecommerce.data.repo

import com.aman.ecommerce.data.model.Product
import io.reactivex.Single

interface ProductRepoI {
    fun getProductList(): Single<Product>
}
