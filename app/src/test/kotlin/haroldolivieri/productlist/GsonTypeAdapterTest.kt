package haroldolivieri.productlist

import haroldolivieri.productlist.repository.remote.ProductsTypeAdapterFactory
import org.junit.Test
import com.google.gson.GsonBuilder
import haroldolivieri.productlist.repository.remote.ProductResponse
import org.junit.Before


class GsonTypeAdapterTest {

    val productsTypeAdapterFactory = ProductsTypeAdapterFactory()

    val gsonBuilder = GsonBuilder()
    val gson = gsonBuilder
            .registerTypeAdapterFactory(ProductsTypeAdapterFactory())
            .create()

    private lateinit var list: List<ProductResponse>

    @Before
    fun setup() {
        val inputStream = ClassLoader.getSystemResourceAsStream("products.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        list = gson.fromJson(jsonString, Array<ProductResponse>::class.java).toList()
    }

    @Test
    fun test_gson_conversion_with_nested_list() {
        assert(list.map { it.id } == listOf(234, 235, 237, 239, 240, 241, 242,
                243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255))
    }

    @Test
    fun test_conversion_from_remote_product_to_interface() {
        assert(list[0].imageResponse?.url.equals(list[0].imageUrl))
    }
}