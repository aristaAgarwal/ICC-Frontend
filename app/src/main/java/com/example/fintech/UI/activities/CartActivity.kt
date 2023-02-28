package com.example.fintech.UI.activities

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fintech.R
import com.example.fintech.adapter.CartItemAdapter
import com.example.fintech.adapter.ProductCardAdapter
import com.example.fintech.constants.AppPreferences
import com.example.fintech.databinding.ActivityCartBinding
import com.example.fintech.model.AddCartData
import com.example.fintech.model.Product
import com.example.fintech.viewModel.MainViewModel
import java.io.Serializable

class CartActivity : AppCompatActivity() {
    lateinit var binding: ActivityCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        getAllProducts()
    }

    fun init() {
        binding.back.setOnClickListener {
            this.finish()
        }
    }

    fun getAllProducts() {
        val mainViewModel by viewModels<MainViewModel>()
        Log.e("cartActivity", "fetching kr rhe hai")
        mainViewModel.getAllProducts(AppPreferences(this).cookies)
        mainViewModel.addProductApiCaller.observe(
            this
        ) {

            Log.e("cartActivity", "fetch ho gya")
            if (it != null) {
                addCartItem(it.data.products)

                Log.e("cartActivity", "fetch ho gya sahi hai data")
            }
        }
    }

    fun addCartItem(product: List<Product>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.CartRv.layoutManager = layoutManager
        binding.CartRv.adapter = CartItemAdapter(this, product)
    }

}