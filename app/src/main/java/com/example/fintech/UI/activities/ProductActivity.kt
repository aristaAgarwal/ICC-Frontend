package com.example.fintech.UI.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fintech.databinding.ActivityProductBinding

class ProductActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}