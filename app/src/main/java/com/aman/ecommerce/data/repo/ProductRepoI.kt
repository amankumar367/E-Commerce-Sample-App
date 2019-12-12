package com.aman.ecommerce.data.repo

import com.aman.ecommerce.data.model.Products
import io.reactivex.Single

interface ProductRepoI {
    fun getProductList(): Single<Products>
}
