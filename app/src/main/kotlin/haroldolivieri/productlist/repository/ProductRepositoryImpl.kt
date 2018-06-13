package haroldolivieri.productlist.repository

import haroldolivieri.productlist.domain.Product
import haroldolivieri.productlist.repository.local.ProductDAO
import haroldolivieri.productlist.repository.local.toProductEntity
import haroldolivieri.productlist.repository.remote.ProductService
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * The repository pattern is a good choice when we need to retrieve data from
 * different source but we might keep that behind the scenes for the presenter layer
 */
class ProductRepositoryImpl
@Inject constructor(private val productDAO: ProductDAO,
                    private val productService: ProductService) : ProductRepository {

    override fun fetchProductList(): Single<List<Product>> {
        return remoteStream().onErrorResumeNext { localStream() }
    }

    private fun remoteStream(): Single<List<Product>> = productService.fetchProducts()
            .toObservable()
            .flatMap { Observable.fromArray(it.filter { it != null }.map { it as Product }) }
            .flatMap {
                saveProductList(it)
                Observable.fromIterable(it)
            }.toSortedList({ p1, p2 ->
                (p1?.name ?: "").compareTo(p2?.name ?: "")
            })

    private fun localStream(): Single<List<Product>> = productDAO.fetchProducts()
            .toObservable()
            .flatMap { Observable.fromArray(it.map { it as Product }) }
            .flatMap { Observable.fromIterable(it) }
            .toList()

    private fun saveProductList(products: List<Product>) {
        productDAO.nukeTableCompletable()
                .subscribeOn(Schedulers.io())
                .subscribe({
                    productDAO
                            .insertManyCompletable(products.map { it.toProductEntity() })
                            .subscribe()
                }, {})
    }

}