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
import com.example.fintech.adapter.ViewPagerAdapter
import com.example.fintech.constants.AppPreferences
import com.example.fintech.databinding.FragmentShopBinding
import com.example.fintech.model.AddProductToCart
import com.example.fintech.model.Product
import com.example.fintech.viewModel.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator

class ShopFragment : Fragment() {

    var binding: FragmentShopBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentShopBinding.inflate(inflater, container, false)
        init()
        setTabLayout()
        return binding!!.root
    }

    fun init(){
        binding?.cart?.setOnClickListener{
            val intent = Intent(context, CartActivity::class.java)
            startActivity(intent)
        }
    }

    fun setTabLayout(){
        var fragments = listOf(ProductFragment(), TicketFragment())
        binding?.pager?.adapter = ViewPagerAdapter(this, fragments)

        TabLayoutMediator(
            binding!!.tabLayout, binding!!.pager
        ) { tab, position ->
            tab.text = when (position) {
                0 -> "Products"
                1 -> "Tickets"
                else -> {
                    ""
                }
            }
        }.attach()
    }

}