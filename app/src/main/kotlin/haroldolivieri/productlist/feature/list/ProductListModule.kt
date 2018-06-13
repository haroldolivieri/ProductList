package haroldolivieri.productlist.feature.list

import dagger.Module
import dagger.Provides
import haroldolivieri.productlist.repository.ProductRepository
import haroldolivieri.productlist.repository.ProductRepositoryImpl

@Module
class ProductListModule {

    @Provides
    fun provideActivity(activity: ProductListActivity) :
            ProductListContract.View = activity

    @Provides
    fun providePresenter(presenter: ProductListPresenter) :
            ProductListContract.Presenter = presenter

    @Provides
    fun provideRepository(repository: ProductRepositoryImpl) : ProductRepository =
            repository
}