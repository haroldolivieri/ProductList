package haroldolivieri.productlist.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import haroldolivieri.productlist.repository.remote.RemoteModule
import haroldolivieri.productlist.feature.list.ProductListActivity
import haroldolivieri.productlist.feature.list.ProductListModule

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector(modules = [RemoteModule::class, ProductListModule::class])
    internal abstract fun bindMainActivity(): ProductListActivity
}