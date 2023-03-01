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
import com.example.fintech.model.AddProductToCart
import com.example.fintech.model.CartIdDO
import com.example.fintech.model.Product
import com.example.fintech.viewModel.MainViewModel
import java.io.Serializable

class CartActivity : AppCompatActivity(), CartItemAdapter.AppLinkClick {
    lateinit var binding: ActivityCartBinding
    var cartId: String? = null
    val mainViewModel by viewModels<MainViewModel>()
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

        binding.checkout.setOnClickListener {
            checkout()
        }
    }

    fun checkout() {

        val cartId =  CartIdDO(cartId!!)
        mainViewModel.checkout(cartId, AppPreferences(this).cookies)
        mainViewModel.checkoutApiCaller.observe(
            this
        ){
            if(it != null){
                Log.e("CartActivity", it.toString())
            }
        }

    }

    fun getAllProducts() {
        mainViewModel.getAllProducts(AppPreferences(this).cookies)
        mainViewModel.addProductApiCaller.observe(
            this
        ) {
            if (it != null) {
                if (it.data != null){
                    addCartItem(it.data.products)
                    cartId = it.data._id
                }
            }
        }
    }

    fun addCartItem(product: List<Product>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.CartRv.layoutManager = layoutManager
        binding.CartRv.adapter = CartItemAdapter(this, product, this)
    }

    override fun onAppLinkClicked(uuid: String, size: String) {
        val product = AddProductToCart(uuid, size)
        mainViewModel.removeProduct(product, AppPreferences(this).cookies)
        mainViewModel.addProductApiCaller.observe(
            this
        ) {
            if (it != null) {
                if (it.data != null) Log.e("CartActivity", it.data.toString())
            }
        }

    }

}