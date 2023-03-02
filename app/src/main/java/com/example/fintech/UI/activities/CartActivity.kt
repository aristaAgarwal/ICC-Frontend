package com.example.fintech.UI.activities

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fintech.R
import com.example.fintech.adapter.CartItemAdapter
import com.example.fintech.constants.AppPreferences
import com.example.fintech.databinding.ActivityCartBinding
import com.example.fintech.model.AddProductToCart
import com.example.fintech.model.CartIdDO
import com.example.fintech.model.Product
import com.example.fintech.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_cart.*


class CartActivity : AppCompatActivity(), CartItemAdapter.AppLinkClick {
    lateinit var binding: ActivityCartBinding
    var cartId: String? = null
    var total: Int = 0
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

    }

    fun checkout() {

        val cartId = CartIdDO(cartId!!)
        mainViewModel.checkout(cartId, AppPreferences(this).cookies)
        mainViewModel.checkoutApiCaller.observe(
            this
        ) {
            if (it != null) {
                Log.e("CartActivity", it.toString())
                binding.webView.isVisible = true
                showWebView(it.data.meta.payment_link)
            }
        }

    }

    @SuppressLint("SetTextI18n")
    fun getAllProducts() {
        mainViewModel.getAllProducts(AppPreferences(this).cookies)
        mainViewModel.addProductApiCaller.observe(
            this
        ) {
            if (it != null) {
                if (it.data != null) {
                    addCartItem(it.data.products)
                    cartId = it.data._id
                    if (it.data.products.isEmpty()) {
                        binding.cartDetails.isVisible = false
                        binding.emptyCart.isVisible = true
                    } else {
                        binding.emptyCart.isVisible = false
                        binding.cartDetails.isVisible = true
                        binding.checkout.setCardBackgroundColor(Color.WHITE)
                        var total = 0
                        it.data.products.forEach {
                            total += it.price
                        }
                        binding.subTotal.text = getString(R.string.rupee)+total.toString()
                        binding.total.text = getString(R.string.rupee)+(total - 20).toString()
                        binding.checkout.setOnClickListener {
                            checkout()
                        }
                    }
                } else {
                    binding.cartDetails.isVisible = false
                    binding.emptyCart.isVisible = true
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
                Toast.makeText(this, "Product deleted Successfully", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun showWebView(paymentLink: String) {

        val webView = binding.webView
        webView.loadUrl(paymentLink)
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                Log.e("url",url)
//                view.loadUrl(url)
                if(url.contains("https://icc-hack.ap-south-1.elasticbeanstalk.com/test")) {
                    webView.removeView(view)
                    view.removeAllViews()
                    view.clearHistory()
                    view.destroy()
                    onBackPressed()
                }
                return false
            }

        }

    }
    override fun onBackPressed() {
        if (webView != null && webView.canGoBack()) webView.goBack() // if there is previous page open it
        else super.onBackPressed() //if there is no previous page, close app
    }
}