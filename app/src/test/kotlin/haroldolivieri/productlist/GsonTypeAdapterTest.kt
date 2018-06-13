package haroldolivieri.productlist

import haroldolivieri.productlist.repository.remote.ProductsTypeAdapterFactory
import org.junit.Test
import com.google.gson.GsonBuilder
import haroldolivieri.productlist.repository.remote.ProductResponse


class GsonTypeAdapterTest {

    val productsTypeAdapterFactory = ProductsTypeAdapterFactory()


    @Test
    fun test_gson_conversion_with_nested_list() {

        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder
                .registerTypeAdapterFactory(ProductsTypeAdapterFactory())
                .create()

        val inputStream = ClassLoader.getSystemResourceAsStream("products.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val result = gson.fromJson(jsonString, Array<ProductResponse>::class.java).toList()

        assert(result.map { it.id } == listOf(234, 235, 237, 239, 240, 241, 242,
                243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255))
    }
}