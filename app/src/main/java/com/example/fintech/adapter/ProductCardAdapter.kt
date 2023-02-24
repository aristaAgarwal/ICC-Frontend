package com.example.fintech.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fintech.R
import com.example.fintech.model.AllProductsDO
import com.example.fintech.model.Product

class ProductCardAdapter(
    var context: Context,
    var allProductsDO: AllProductsDO,
    var appLinkListener: AppLinkClick
    ) :
    RecyclerView.Adapter<ProductCardAdapter.RACItemHolder>()  {


    inner class RACItemHolder(v: View): RecyclerView.ViewHolder(v) {
        private var view: View = v
        val image = view.findViewById<ImageView>(R.id.product_image)
        var title = view.findViewById<TextView>(R.id.product_title)
        fun bindItem(product: Product) {
//            image = product.display_image

            Log.e("in Adapter", product.toString())
            title.text = product.name

        }
    }

    override fun getItemCount(): Int = allProductsDO.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RACItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.product_card_layout,
            parent, false)

        Log.e("in Adapter", allProductsDO.toString())
        this.context = parent.context
        return RACItemHolder(view)
    }

    override fun onBindViewHolder(holder: RACItemHolder, position: Int) {
        holder.bindItem(allProductsDO[position])
    }

    interface AppLinkClick {
        fun onAppLinkClicked(id: String, date: String)
    }
}