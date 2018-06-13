package haroldolivieri.productlist.repository.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface ProductDAO {

    @Query("SELECT * FROM productEntity")
    fun fetchProducts() : Single<List<ProductEntity>>

    @Insert
    fun insert(product: ProductEntity)
}