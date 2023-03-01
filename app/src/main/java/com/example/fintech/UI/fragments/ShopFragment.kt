package com.example.fintech.UI.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fintech.UI.activities.CartActivity
import com.example.fintech.UI.activities.ProductActivity
import com.example.fintech.adapter.ProductCardAdapter
import com.example.fintech.constants.AppPreferences
import com.example.fintech.databinding.FragmentShopBinding
import com.example.fintech.model.AddProductToCart
import com.example.fintech.model.Product
import com.example.fintech.viewModel.MainViewModel

class ShopFragment : Fragment(), ProductCardAdapter.AppLinkClick, java.io.Serializable {

    var binding: FragmentShopBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentShopBinding.inflate(inflater, container, false)
        getProducts()
        init()
        return binding!!.root
    }

    fun init(){
        binding?.cart?.setOnClickListener{
            val intent = Intent(context, CartActivity::class.java)
            startActivity(intent)
        }
    }

    fun getProducts() {
        val mainViewModel by viewModels<MainViewModel>()
        mainViewModel.getProducts()
        mainViewModel.productApiCaller.observe(
            viewLifecycleOwner
        ) {
            if (it.code == 200) setProductsAdapter(it.data)
        }
    }

    fun setProductsAdapter(data: List<Product>) {
        Log.e("shopActivity", "In here")
        val layoutManager = GridLayoutManager(requireContext(), 2)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding?.productsRv?.layoutManager = layoutManager
        binding?.productsRv?.adapter?.notifyDataSetChanged()
        binding?.productsRv?.adapter = ProductCardAdapter(requireContext(), data, this)
    }

    override fun onAppLinkClicked(product: Product, layout: String) {
        if (layout == "product") {
            val intent = Intent(context, ProductActivity::class.java)
            intent.putExtra("product", product)
            startActivity(intent)
        }
        else {
            addToCart(product.uuid, "L")
        }

    }

    fun addToCart(productId: String, size: String){
        val mainViewModel by viewModels<MainViewModel>()
        val product = AddProductToCart(productId, size)
        mainViewModel.addProducts(product, AppPreferences(context).cookies)
        mainViewModel.addProductApiCaller.observe(
            this
        ){
            if (it != null)
                Log.e("CartActivity", it.toString())
            Toast.makeText(context, "Product added successfully",Toast.LENGTH_SHORT).show()
        }
    }
}