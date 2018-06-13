package haroldolivieri.productlist.feature.list

import android.os.Bundle

import haroldolivieri.productlist.R
import haroldolivieri.productlist.feature.BaseActivity

class ProductListActivity(override val layout: Int = R.layout.activity_product_list) :
        BaseActivity(), ProductListContract.View {

    override val internetChangesCallback: (Boolean) -> Unit = {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
