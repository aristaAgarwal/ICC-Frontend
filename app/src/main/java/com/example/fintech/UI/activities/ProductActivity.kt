package com.example.fintech.UI.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.annotation.Nullable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.fintech.constants.AppPreferences
import com.example.fintech.databinding.ActivityProductBinding
import com.example.fintech.model.Product

class ProductActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val product = intent.getSerializableExtra("product") as Product
        setContent(product)
        init()
    }

    fun init(){
        binding.back.setOnClickListener{
            this.finish()
        }

        binding.addToCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }

    fun setContent(product: Product) {
        loadImage(product.display_image, binding.productImage)
        binding.productTitle.text = product.name
        binding.expandDescriptionTextView.setText(product.description)
    }

    fun loadImage(imageUrl: String, imageView: ImageView) {
        Glide.with(this).asBitmap().load(imageUrl).into(object : CustomTarget<Bitmap?>() {
            override fun onResourceReady(
                resource: Bitmap, @Nullable transition: Transition<in Bitmap?>?
            ) {
                imageView.setImageBitmap(resource)
            }

            override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
        })
    }
}