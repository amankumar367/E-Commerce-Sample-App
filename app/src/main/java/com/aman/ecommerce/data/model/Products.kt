package com.aman.ecommerce.data.model


import com.google.gson.annotations.SerializedName

data class Products(
    @SerializedName("products")
    val products: List<Product?>?
) {
    data class Product(
        @SerializedName("description")
        val description: String?,
        @SerializedName("href")
        val href: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("image")
        val image: String?,
        @SerializedName("images")
        val images: List<Any?>?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("options")
        val options: List<Any?>?,
        @SerializedName("price")
        val price: String?,
        @SerializedName("product_id")
        val productId: String?,
        @SerializedName("quantity")
        val quantity: Int?,
        @SerializedName("sku")
        val sku: String?,
        @SerializedName("special")
        val special: String?,
        @SerializedName("thumb")
        val thumb: String?,
        @SerializedName("zoom_thumb")
        val zoomThumb: String?
    )
}