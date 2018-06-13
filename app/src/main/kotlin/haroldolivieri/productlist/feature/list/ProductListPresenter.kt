package haroldolivieri.productlist.feature.list

import haroldolivieri.productlist.repository.ProductRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class ProductListPresenter @Inject constructor(val view: ProductListContract.View,
                                               val repository: ProductRepository) :
        ProductListContract.Presenter {

    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun onCreate() {
        refreshProducts()
    }

    override fun onDestroy() {
        disposables.dispose()
    }

    override fun refreshProducts() {
        disposables.add(repository.fetchProductList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ view.showList(it)},
                        { t -> view.showError(t.message)}))
    }
}

