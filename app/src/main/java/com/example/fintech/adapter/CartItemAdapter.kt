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
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.fintech.R
import com.example.fintech.model.Product

class CartItemAdapter(
    var context: Context,
    var product: List<Product>,
    var onAppLinkClick: AppLinkClick
) : RecyclerView.Adapter<CartItemAdapter.RACItemHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RACItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.cart_item_layout, parent, false
        )
        this.context = parent.context
        return RACItemHolder(view)
    }

    override fun getItemCount() = product.size

    inner class RACItemHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        var image = view.findViewById<ImageView>(R.id.product_image)
        var title = view.findViewById<TextView>(R.id.product_title)
        var description = view.findViewById<TextView>(R.id.product_description)
        var mrp = view.findViewById<TextView>(R.id.mrp)
        var discountedMrp = view.findViewById<TextView>(R.id.discounted_mrp)
        var delete = view.findViewById<ImageView>(R.id.delete)
        fun bindItem(product: Product) {

            loadImage(product.display_image, image)
            title.text = product.name
            description.text = product.description
            mrp.text = product.price.toString()
            mrp.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            discountedMrp.text = (product.price * 0.9).toString()
            description.isSingleLine = true
            Log.e("CartAdapter", product.toString())
            delete.setOnClickListener{

                onAppLinkClick.onAppLinkClicked(product.uuid, product.sizes[0])
            }
        }
    }

    fun loadImage(imageUrl: String, imageView: ImageView) {
        Glide.with(context).asBitmap().load(imageUrl).into(object : CustomTarget<Bitmap?>() {
            override fun onResourceReady(
                resource: Bitmap, transition: Transition<in Bitmap?>?
            ) {
                imageView.setImageBitmap(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    }

    override fun onBindViewHolder(holder: RACItemHolder, position: Int) {
        holder.bindItem(product[position])
    }

    interface AppLinkClick {
        fun onAppLinkClicked(uuid: String, s1: String)
    }
}