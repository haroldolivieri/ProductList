package haroldolivieri.productlist.repository.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ProductDAO {

    companion object {
        private const val TABLE_NAME = "productEntity"
    }

    @Query("SELECT * FROM $TABLE_NAME" +
            "ORDER BY name ASC")
    fun fetchProducts(): Single<List<ProductEntity>>

    @Insert
    fun insertMany(product: List<ProductEntity>): Completable

    @Query("DELETE FROM $TABLE_NAME")
    fun nukeTable(): Completable
}