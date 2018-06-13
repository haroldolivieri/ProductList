package haroldolivieri.productlist.feature.list

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import haroldolivieri.productlist.R
import haroldolivieri.productlist.domain.Product
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


class ProductAdapter(private var adapterList: List<Product>? = null,
                     private val onClickItem: (Product) -> Unit,
                     private val context: Context) :
        RecyclerView.Adapter<ProductAdapter.ProductHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product, parent, false)
        return ProductHolder(view)
    }

    fun setProducts(products: List<Product>) {
        this.adapterList = products
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        adapterList?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = adapterList?.size ?: 0


    inner class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val image = itemView.findViewById<ImageView>(R.id.image)
        private val productName = itemView.findViewById<TextView>(R.id.name)
        private val productBrand = itemView.findViewById<TextView>(R.id.brand)
        private val productCurrentPrice = itemView.findViewById<TextView>(R.id.currentPrice)
        private val productOriginalPrice = itemView.findViewById<TextView>(R.id.originalPrice)

        @SuppressLint("SetTextI18n")
        fun bind(product: Product) {

            val options = RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background)
                    .priority(Priority.HIGH)

            Glide.with(context)
                    .load(product.imageUrl)
                    .apply(options)
                    .into(image)

            val numberFormat = NumberFormat
                    .getNumberInstance(Locale.ENGLISH) as DecimalFormat
            numberFormat.applyPattern("#.##")

            product.apply {
                productName.text = name
                productBrand.text = brand

                productCurrentPrice.text = "$${numberFormat.format(currentPrice)} - $currency"

                if(currentPrice == originalPrice) {
                    productOriginalPrice.visibility = View.INVISIBLE
                } else {
                    productOriginalPrice.visibility = View.VISIBLE
                    productOriginalPrice.text = "$${numberFormat.format(originalPrice)} - $currency"
                }

                itemView.setOnClickListener { onClickItem.invoke(this) }
            }
        }
    }
}