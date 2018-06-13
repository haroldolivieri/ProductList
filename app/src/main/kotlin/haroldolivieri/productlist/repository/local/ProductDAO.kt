package haroldolivieri.productlist.repository.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
abstract class ProductDAO {

    companion object {
        private const val TABLE_NAME = "productEntity"
    }

    @Query("SELECT * FROM $TABLE_NAME ORDER BY name ASC")
    abstract fun fetchProducts(): Single<List<ProductEntity>>

    @Insert
    abstract fun insertMany(product: List<ProductEntity>)

    @Query("DELETE FROM $TABLE_NAME")
    abstract fun nukeTable()

    open fun insertManyCompletable(products: List<ProductEntity>): Completable {
        return Completable.fromAction { insertMany(products) }
    }

    open fun nukeTableCompletable(): Completable {
        return Completable.fromAction { nukeTable() }
    }
}