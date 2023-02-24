package com.example.fintech.UI.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fintech.adapter.ProductCardAdapter
import com.example.fintech.databinding.ActivityShopBinding
import com.example.fintech.model.AllProductsDO
import com.example.fintech.model.ProductsDO
import com.example.fintech.viewModel.MainViewModel

class ShopActivity : AppCompatActivity(), ProductCardAdapter.AppLinkClick {
    lateinit var binding: ActivityShopBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getProducts()
    }

    fun getProducts(){
        val mainViewModel by viewModels<MainViewModel>()
        mainViewModel.getProducts()
        mainViewModel.productApiCaller.observe(
            this
        ){
            Log.e("getProduts", it.data.toString())

                Log.e("scddd", (it.data is AllProductsDO).toString())
            if (it.data is AllProductsDO)
                setProductsAdapter(it.data)
        }
    }

    fun setProductsAdapter(data: AllProductsDO) {
        Log.e("shopActivity", "In here")
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.productsRv.layoutManager = layoutManager
        binding.productsRv.adapter?.notifyDataSetChanged()
        binding.productsRv.adapter = ProductCardAdapter(this, data, this)
    }
    override fun onAppLinkClicked(id: String, date: String) {

    }
}