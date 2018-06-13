package haroldolivieri.productlist.repository.local

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides


@Module
class LocalModule {

    @Provides
    fun provideDatabase(context : Context) : ProductDatabase =
        Room.databaseBuilder(context, ProductDatabase::class.java, "ProductList_DB").build()

    @Provides
    fun provideProductDAO(roomDatabase: ProductDatabase) : ProductDAO =
            roomDatabase.productDao()

}