package com.aman.ecommerce.persistant

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aman.ecommerce.data.model.Products

@Dao
interface ProductDao {

    @Insert
    fun insertProduct(person: Products.Product?)

    @Insert
    fun insertMultipleProduct(person: List<Products.Product>?)

    @Query("SELECT * FROM PRODUCT ORDER BY id")
    fun getAllProducts(): List<Products.Product>?
}