package com.example.fintech.UI.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fintech.UI.activities.ProductActivity
import com.example.fintech.adapter.ProductCardAdapter
import com.example.fintech.databinding.FragmentShopBinding
import com.example.fintech.model.AllProductsDO
import com.example.fintech.model.Product
import com.example.fintech.viewModel.MainViewModel

class ShopFragment : Fragment(), ProductCardAdapter.AppLinkClick {

    var binding: FragmentShopBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentShopBinding.inflate(inflater, container, false)
        getProducts()
        return binding!!.root
    }

    fun getProducts(){
        val mainViewModel by viewModels<MainViewModel>()
        mainViewModel.getProducts()
        mainViewModel.productApiCaller.observe(
            viewLifecycleOwner
        ){
            if (it.code == 200)
                setProductsAdapter(it.data)
        }
    }

    fun setProductsAdapter(data: AllProductsDO) {
        Log.e("shopActivity", "In here")
        val layoutManager = GridLayoutManager(requireContext(),2)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding?.productsRv?.layoutManager = layoutManager
        binding?.productsRv?.adapter?.notifyDataSetChanged()
        binding?.productsRv?.adapter = ProductCardAdapter(requireContext(), data, this)
    }
    override fun onAppLinkClicked(product: Product) {
        val intent = Intent(context, ProductActivity::class.java)

        startActivity(intent)
    }
}