package com.example.fintech.UI.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.fintech.constants.AppPreferences
import com.example.fintech.databinding.ActivityCartBinding
import com.example.fintech.model.AddProductToCart
import com.example.fintech.viewModel.MainViewModel

class CartActivity : AppCompatActivity() {
    lateinit var binding: ActivityCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun addToCart(productId: String, size: String){
        val mainViewModel by viewModels<MainViewModel>()
        val product = AddProductToCart(productId, size)
        mainViewModel.addProducts(product, AppPreferences(this).cookies)
        mainViewModel.productApiCaller.observe(
            this
        ){
            if (it != null)
                Log.e("CartActivity", it.toString())
        }
    }
}