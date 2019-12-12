package com.aman.ecommerce.data.repo

import com.aman.ecommerce.data.model.Product
import io.reactivex.Single

class ProductRepo: ProductRepoI {
    override fun getProductList(): Single<Product> {
        return Single.create<Product> {

        }
    }
}