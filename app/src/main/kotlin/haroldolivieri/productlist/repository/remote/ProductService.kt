package haroldolivieri.productlist.repository.remote

import io.reactivex.Single
import retrofit2.http.GET

/*
The ProductResponse needs to be nullable because the API return a comma at the end of the
products payload and the gsonAdapter understands that as a null object
 */
interface ProductService {
    @GET("/products")
    fun fetchProducts() : Single<List<ProductResponse?>>
}