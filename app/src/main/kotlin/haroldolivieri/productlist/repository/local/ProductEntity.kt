package haroldolivieri.productlist.repository.local

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import haroldolivieri.productlist.domain.Product

@Entity
class ProductEntity(@PrimaryKey override val id: Int,
                    override val name: String = "",
                    override val brand: String = "",
                    override val originalPrice: Float = 0F,
                    override val currentPrice: Float = 0F,
                    override val currency: String = "",
                    override val imageUrl: String = "") : Product

fun Product.toProductEntity() : ProductEntity =
        ProductEntity(id, name, brand, originalPrice, currentPrice, currency, imageUrl)
