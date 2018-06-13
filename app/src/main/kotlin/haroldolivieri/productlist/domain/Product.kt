package haroldolivieri.productlist.domain

import org.joda.money.CurrencyUnit

interface Product {
    val id: Int
    val name: String
    val brand: String
    val originalPrice: Float
    val currentPrice: Float
    val currency: String
    val imageUrl: String
}







