package haroldolivieri.productlist.repository.local

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import dagger.Module
import dagger.Provides


@Module
class LocalModule {

    @Provides
    fun provideDatabase(context : Context) : RoomDatabase =
        Room.databaseBuilder(context, ProductDatabase::class.java, "we-need-db").build()

}