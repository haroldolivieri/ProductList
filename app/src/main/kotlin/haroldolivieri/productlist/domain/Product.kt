package haroldolivieri.productlist.domain


/**
 * This interface ensure that in different sources of data I will always follow the
 * same protocol defined here (ProductEntity and ProductResponse implements this interface)
 */
interface Product {
    val id: Int
    val name: String
    val brand: String
    val originalPrice: Float
    val currentPrice: Float
    val currency: String
    val imageUrl: String
}







