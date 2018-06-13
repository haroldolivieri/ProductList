package haroldolivieri.productlist.feature.list

import dagger.Module
import dagger.Provides

@Module
class ProductListModule {

    @Provides
    fun provideActivity(activity: ProductListActivity) :
            ProductListContract.View = activity

    @Provides
    fun providePresenter(presenter: ProductListPresenter) :
            ProductListContract.Presenter = presenter
}