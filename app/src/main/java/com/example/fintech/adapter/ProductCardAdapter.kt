package com.example.fintech.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.fintech.R
import com.example.fintech.model.Product

class ProductCardAdapter(
    var context: Context,
    var allProductsDO: List<Product>,
    var appLinkListener: AppLinkClick
) : RecyclerView.Adapter<ProductCardAdapter.RACItemHolder>() {


    inner class RACItemHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        val product_layout = view.findViewById<LinearLayout>(R.id.product)
        val image = view.findViewById<ImageView>(R.id.product_image)
        var title = view.findViewById<TextView>(R.id.product_title)
        var description = view.findViewById<TextView>(R.id.product_description)
        var mrp = view.findViewById<TextView>(R.id.mrp)
        var discountedMrp = view.findViewById<TextView>(R.id.discounted_mrp)
        var discount = view.findViewById<TextView>(R.id.discount)
        var addButton = view.findViewById<CardView>(R.id.add_button)
        fun bindItem(product: Product) {
//            image = product.display_image
            Log.e("in Adapter", product.description.toString())
            title.text = product.name
            description.text = product.description
            mrp.text = product.price.toString()
            mrp.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            discountedMrp.text = product.discount.toString()
            description.isSingleLine = true

            loadImage(product.display_image, image)
            product_layout.setOnClickListener {
                appLinkListener.onAppLinkClicked(product, "product")
            }

            addButton.setOnClickListener {
                appLinkListener.onAppLinkClicked(product, "cart")
            }
        }
    }

    fun loadImage(imageUrl: String, imageView: ImageView) {
        Glide.with(context).asBitmap().load(imageUrl).into(object : CustomTarget<Bitmap?>() {
            override fun onResourceReady(
                resource: Bitmap, @Nullable transition: Transition<in Bitmap?>?
            ) {
                imageView.setImageBitmap(resource)
            }

            override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
        })
    }

    override fun getItemCount(): Int = allProductsDO.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RACItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.product_card_layout, parent, false
        )
        this.context = parent.context
        return RACItemHolder(view)
    }

    override fun onBindViewHolder(holder: RACItemHolder, position: Int) {
        holder.bindItem(allProductsDO[position])
    }

    interface AppLinkClick {
        fun onAppLinkClicked(product: Product, layout: String)
    }
}