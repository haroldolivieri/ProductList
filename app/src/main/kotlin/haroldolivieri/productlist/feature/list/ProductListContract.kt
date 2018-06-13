package haroldolivieri.productlist.feature.list

import haroldolivieri.productlist.domain.Product


object ProductListContract {
    interface View {
        fun showList(products: List<Product>)
        fun showError(message: String?)
    }

    interface Presenter {
        fun onCreate()
        fun onDestroy()
        fun refreshProducts()
    }
}