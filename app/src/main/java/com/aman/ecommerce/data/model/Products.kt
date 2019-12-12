package com.aman.ecommerce.data.model


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

data class Products(
    @SerializedName("products")
    val products: List<Product?>?
) {
    @Entity(tableName = "product")
    @Parcelize
    data class Product(

        @PrimaryKey
        @SerializedName("id")
        var id: String,

        @ColumnInfo(name = "image")
        @SerializedName("image")
        var image: String?,

        @ColumnInfo(name = "name")
        @SerializedName("name")
        var name: String?,

        @ColumnInfo(name = "price")
        @SerializedName("price")
        var price: String?,

        @ColumnInfo(name = "special")
        @SerializedName("special")
        var special: String?,

        @ColumnInfo(name = "quantity")
        @SerializedName("quantity")
        var quantity: Int?,

        @Ignore
        @SerializedName("description")
        val description: String?,

        @Ignore
        @SerializedName("href")
        val href: String?,

        @Ignore
        @SerializedName("images")
        val images: List<@RawValue Any?>?,

        @Ignore
        @SerializedName("options")
        val options: List<@RawValue Any?>?,

        @Ignore
        @SerializedName("product_id")
        val productId: String?,

        @Ignore
        @SerializedName("sku")
        val sku: String?,

        @Ignore
        @SerializedName("thumb")
        val thumb: String?,

        @Ignore
        @SerializedName("zoom_thumb")
        val zoomThumb: String?
    ) : Parcelable {
        constructor() : this(
            id = "0", image = "",  name = "", price = "", special= "", quantity = 0,
            description = "", href = "", images = null, options = null, productId = "", sku = "",
            thumb = "", zoomThumb = "")
    }
}