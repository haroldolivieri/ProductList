package haroldolivieri.productlist.feature.list

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log

import haroldolivieri.productlist.R
import haroldolivieri.productlist.domain.Product
import haroldolivieri.productlist.feature.BaseActivity
import kotlinx.android.synthetic.main.list_content.*
import javax.inject.Inject

/**
 * The activity should resolve only view questions. That's why the only thing that it knows
 * about the model is the interface (Product)
 */

class ProductListActivity(override val layout: Int = R.layout.activity_product_list) :
        BaseActivity(), ProductListContract.View {

    @Inject lateinit var presenter: ProductListContract.Presenter

    private val productAdapter by lazy {
        ProductAdapter(onClickItem = {
            Log.d(TAG, "item ${it.id} clicked")
            //open image on fullscreen
        }, context = this@ProductListActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.onCreate()
        setupRecyclerView()
    }

    override fun showError(message: String?) {
        showSnackBar(productList, message ?: "Unknown error")
    }

    override fun showList(products: List<Product>) {
        productAdapter.setProducts(products)
    }

    override val internetChangesCallback: (Boolean) -> Unit = { connected ->
        if (!connected) {
            showSnackBar(productList, R.string.check_internet_conn, Snackbar.LENGTH_INDEFINITE)
        } else {
            hideSnackBar()
        }
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(productList.context,
                linearLayoutManager.orientation)

        productList.addItemDecoration(dividerItemDecoration)
        productList.layoutManager = linearLayoutManager
        productList.adapter = productAdapter
        productList.setEmptyView(emptyView)
    }
}
