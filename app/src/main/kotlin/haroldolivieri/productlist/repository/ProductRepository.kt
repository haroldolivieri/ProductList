package haroldolivieri.productlist.repository

import haroldolivieri.productlist.domain.Product
import haroldolivieri.productlist.repository.remote.ProductResponse
import io.reactivex.Single


interface ProductRepository {
    fun fetchProductList(): Single<List<Product>>
}