package haroldolivieri.productlist

import haroldolivieri.productlist.repository.ProductRepositoryImpl
import haroldolivieri.productlist.repository.local.ProductDAO
import haroldolivieri.productlist.repository.local.ProductEntity
import haroldolivieri.productlist.repository.remote.ProductResponse
import haroldolivieri.productlist.repository.remote.ProductService
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*


class RespositoryTest {

    @Rule
    @JvmField val schedulers = RxImmediateSchedulerRule()

    @Mock lateinit var productDAO: ProductDAO
    @Mock lateinit var productService: ProductService

    @InjectMocks lateinit var repository: ProductRepositoryImpl

    private val remoteList = listOf(ProductResponse(id = 1),
            ProductResponse(id = 2),ProductResponse(id = 3), ProductResponse(id = 4))


    private val localList = listOf(ProductEntity(id = 1),
            ProductEntity(id = 2),ProductEntity(id = 5))

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun test_remote_brings_correct_data() {
        `when`(productService.fetchProducts()).thenReturn(Single.just(remoteList))
        `when`(productDAO.fetchProducts()).thenReturn(Single.just(localList))
        `when`(productDAO.nukeTable()).thenReturn(Completable.complete())
        `when`(productDAO.insertMany(ArgumentMatchers.anyList())).thenReturn(Completable.complete())

        val testObserver = repository.fetchProductList().test()

        testObserver.assertNoErrors()
        testObserver.assertComplete()
        Assert.assertTrue(testObserver.values()[0].size == remoteList.size)

        verify(productDAO).insertMany(ArgumentMatchers.anyList())
        verify(productDAO).nukeTable()
    }

    @Test
    fun test_remote_brings_correct_data_error_on_delete_table() {
        `when`(productService.fetchProducts()).thenReturn(Single.just(remoteList))
        `when`(productDAO.fetchProducts()).thenReturn(Single.just(localList))
        `when`(productDAO.nukeTable()).thenReturn(Completable.error(Throwable()))
        `when`(productDAO.insertMany(ArgumentMatchers.anyList())).thenReturn(Completable.complete())

        val testObserver = repository.fetchProductList().test()

        testObserver.assertNoErrors()
        testObserver.assertComplete()
        Assert.assertTrue(testObserver.values()[0].size == remoteList.size)

        verify(productDAO, never()).insertMany(ArgumentMatchers.anyList())
        verify(productDAO).nukeTable()
    }

    @Test
    fun test_remote_brings_correct_from_local() {
        `when`(productService.fetchProducts()).thenReturn(Single.error(Throwable()))
        `when`(productDAO.fetchProducts()).thenReturn(Single.just(localList))
        `when`(productDAO.nukeTable()).thenReturn(Completable.complete())
        `when`(productDAO.insertMany(ArgumentMatchers.anyList())).thenReturn(Completable.complete())

        val testObserver = repository.fetchProductList().test()

        testObserver.assertNoErrors()
        testObserver.assertComplete()
        Assert.assertTrue(testObserver.values()[0].size == localList.size)

        verify(productDAO, never()).insertMany(ArgumentMatchers.anyList())
        verify(productDAO, never()).nukeTable()
    }
}