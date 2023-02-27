package com.example.fintech.UI.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fintech.databinding.ActivityProductBinding
import com.example.fintech.model.Product

class ProductActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val product = intent.getSerializableExtra("product") as Product
    }
}