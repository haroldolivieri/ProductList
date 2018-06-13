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


class RepositoryTest {

    @Rule @JvmField val schedulers = RxImmediateSchedulerRule()

    @Mock private lateinit var productDAO: ProductDAO
    @Mock private lateinit var productService: ProductService

    @InjectMocks private lateinit var repository: ProductRepositoryImpl

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
        `when`(productDAO.nukeTableCompletable()).thenReturn(Completable.complete())
        `when`(productDAO.insertManyCompletable(ArgumentMatchers.anyList()))
                .thenReturn(Completable.complete())

        val testObserver = repository.fetchProductList().test()

        testObserver.assertNoErrors()
        testObserver.assertComplete()
        Assert.assertTrue(testObserver.values()[0].size == remoteList.size)

        verify(productDAO).insertManyCompletable(ArgumentMatchers.anyList())
        verify(productDAO).nukeTableCompletable()
    }

    @Test
    fun test_remote_brings_correct_data_error_on_delete_table() {
        `when`(productService.fetchProducts()).thenReturn(Single.just(remoteList))
        `when`(productDAO.fetchProducts()).thenReturn(Single.just(localList))
        `when`(productDAO.nukeTableCompletable()).thenReturn(Completable.error(Throwable()))
        `when`(productDAO.insertManyCompletable(ArgumentMatchers.anyList()))
                .thenReturn(Completable.complete())

        val testObserver = repository.fetchProductList().test()

        testObserver.assertNoErrors()
        testObserver.assertComplete()
        Assert.assertTrue(testObserver.values()[0].size == remoteList.size)

        verify(productDAO, never()).insertManyCompletable(ArgumentMatchers.anyList())
        verify(productDAO).nukeTableCompletable()
    }

    @Test
    fun test_remote_brings_correct_from_local() {
        `when`(productService.fetchProducts()).thenReturn(Single.error(Throwable()))
        `when`(productDAO.fetchProducts()).thenReturn(Single.just(localList))
        `when`(productDAO.nukeTableCompletable()).thenReturn(Completable.complete())
        `when`(productDAO.insertManyCompletable(ArgumentMatchers.anyList()))
                .thenReturn(Completable.complete())

        val testObserver = repository.fetchProductList().test()

        testObserver.assertNoErrors()
        testObserver.assertComplete()
        Assert.assertTrue(testObserver.values()[0].size == localList.size)

        verify(productDAO, never()).insertManyCompletable(ArgumentMatchers.anyList())
        verify(productDAO, never()).nukeTableCompletable()
    }

    @Test
    fun test_error_in_both_sources() {
        val localError = Throwable("Local error")
        `when`(productService.fetchProducts()).thenReturn(Single.error(Throwable()))
        `when`(productDAO.fetchProducts()).thenReturn(Single.error(localError))
        `when`(productDAO.nukeTableCompletable()).thenReturn(Completable.complete())
        `when`(productDAO.insertManyCompletable(ArgumentMatchers.anyList()))
                .thenReturn(Completable.complete())

        val testObserver = repository.fetchProductList().test()

        testObserver.assertError(localError)

        verify(productDAO, never()).insertManyCompletable(ArgumentMatchers.anyList())
        verify(productDAO, never()).nukeTableCompletable()
    }
}