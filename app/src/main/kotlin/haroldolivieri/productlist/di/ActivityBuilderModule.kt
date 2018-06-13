package haroldolivieri.productlist.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import haroldolivieri.productlist.repository.remote.RemoteModule
import haroldolivieri.productlist.feature.list.ProductListActivity
import haroldolivieri.productlist.feature.list.ProductListModule
import haroldolivieri.productlist.repository.local.LocalModule


/**
 * Working with ActivityBuilderMOdule on dagger-android is a better way to work with
 * scoper subcomponents to ensure the encapsulation and the segregation principle keeps
 * intact
 */

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector(modules = [RemoteModule::class, LocalModule::class,
        ProductListModule::class])
    internal abstract fun bindMainActivity(): ProductListActivity
}