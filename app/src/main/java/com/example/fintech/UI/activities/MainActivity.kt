package com.example.fintech.UI.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.fintech.BuildConfig
import com.example.fintech.R
import com.example.fintech.constants.AppPreferences
import com.example.fintech.databinding.ActivityMainBinding
import com.example.fintech.viewModel.MainViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (AppPreferences(this).cookies == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            this.finish()
        } else {
            signOut()
        }
        init()
    }

    fun init(){
        binding.shop.setOnClickListener {
            val intent = Intent(this, ShopActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }
    private fun signOut() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.CLIENT_ID).requestEmail().build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.signOutButton.setOnClickListener {
            AppPreferences(this).cookies = null
            val mainViewModel by viewModels<MainViewModel>()
            mainViewModel.logout(AppPreferences(this).cookies)
            mainViewModel.apiCaller.observe(this) {
                if (it != null) {
                    Log.e("MainActivity", "logout")
                    mGoogleSignInClient.signOut().addOnCompleteListener(this) {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        this.finish()
                    }
                }
            }
        }
    }
}