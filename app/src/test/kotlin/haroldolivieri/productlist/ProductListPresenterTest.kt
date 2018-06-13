package haroldolivieri.productlist

import haroldolivieri.productlist.feature.list.ProductListContract
import haroldolivieri.productlist.feature.list.ProductListPresenter
import haroldolivieri.productlist.repository.ProductRepository
import haroldolivieri.productlist.repository.remote.ProductResponse
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class ProductListPresenterTest {
    @Rule
    @JvmField val schedulers = RxImmediateSchedulerRule()

    @Mock
    private
    lateinit var repository: ProductRepository

    @Mock
    lateinit var view: ProductListContract.View

    private lateinit var presenter : ProductListPresenter

    private val products = listOf(ProductResponse(id = 1),
            ProductResponse(id = 2),ProductResponse(id = 3), ProductResponse(id = 4))

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = ProductListPresenter(view, repository)
    }

    @Test
    fun test_fetch_data_from_repository() {

        Mockito.`when`(repository.fetchProductList()).thenReturn(Single.just(products))

        presenter.onCreate()

        verify(view).showList(products)
        verify(view, never()).showError(ArgumentMatchers.any())
    }

    @Test
    fun test_fetch_data_from_repository_with_error() {

        val errorMessage = "ERROR"
        Mockito.`when`(repository.fetchProductList())
                .thenReturn(Single.error(Throwable(errorMessage)))

        presenter.onCreate()

        verify(view, never()).showList(products)
        verify(view).showError(errorMessage)
    }
}