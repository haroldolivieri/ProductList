package haroldolivieri.productlist.repository.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [(ProductEntity::class)], version = 1)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDAO
}