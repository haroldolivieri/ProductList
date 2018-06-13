package haroldolivieri.productlist.repository.remote

import com.google.gson.annotations.SerializedName

data class ProductResponse(@SerializedName("identifier") val id: Int,
                           val name: String,
                           val brand: String,
                           @SerializedName("original_price") val originalPrice: Float,
                           @SerializedName("current_price") val currentPrice: Float,
                           val currency: String,
                           @SerializedName("image") val imageResponse: ImageResponse)

data class ImageResponse(val id: Int, val url: String)