package haroldolivieri.productlist.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import haroldolivieri.productlist.ProductListApp
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    ActivityBuilderModule::class])
interface ApplicationComponent : AndroidInjector<ProductListApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}