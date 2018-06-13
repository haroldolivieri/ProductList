package haroldolivieri.productlist.repository.remote

import com.google.gson.annotations.SerializedName
import haroldolivieri.productlist.domain.Product

data class ProductResponse(@SerializedName("identifier")
                           override val id: Int = 0,
                           override val name: String = "",
                           override val brand: String = "",
                           @SerializedName("original_price")
                           override val originalPrice: Float = 0F,
                           @SerializedName("current_price")
                           override val currentPrice: Float = 0F,
                           override val currency: String = "",
                           @SerializedName("image")
                           val imageResponse: ImageResponse? = null) : Product {

    override val imageUrl: String
        get() = imageResponse?.url ?: ""
}

data class ImageResponse(val id: Int, val url: String)