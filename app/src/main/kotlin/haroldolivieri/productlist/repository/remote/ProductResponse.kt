package haroldolivieri.productlist.repository.remote

import com.google.gson.annotations.SerializedName
import haroldolivieri.productlist.domain.Product

data class ProductResponse(@SerializedName("identifier")
                           override val id: Int,
                           override val name: String,
                           override val brand: String,
                           @SerializedName("original_price")
                           override val originalPrice: Float,
                           @SerializedName("current_price")
                           override val currentPrice: Float,
                           override val currency: String,
                           @SerializedName("image")
                           private val imageResponse: ImageResponse) : Product {

    override val imageUrl: String = imageResponse.url
}

data class ImageResponse(val id: Int, val url: String)