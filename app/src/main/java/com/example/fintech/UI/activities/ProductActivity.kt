package com.example.fintech.UI.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.fintech.R
import com.example.fintech.databinding.ActivityProductBinding
import com.example.fintech.model.Product
import com.ms.square.android.expandabletextview.ExpandableTextView

class ProductActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val product = intent.getSerializableExtra("product") as Product
        setContent(product)
    }

    fun setContent(product: Product) {
        loadImage(product.display_image, binding.productImage)
        binding.productTitle.text = product.name
        binding.productDescription.text = product.description
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
        Log.e("product Description",product.description)
        val editTV = binding.expandTextView
        editTV.setText("Description"+"\n\n"+product.description)
    }
}