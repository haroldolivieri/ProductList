package haroldolivieri.productlist.feature.list


object ProductListContract {
    interface View {
        fun showList()
    }

    interface Presenter {
        fun onCreate()
        fun onDestroy()
        fun refreshProducts()
    }
}