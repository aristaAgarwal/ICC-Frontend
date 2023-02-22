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
import com.example.fintech.viewModel.MainViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException


class MainActivity : AppCompatActivity() {
    private var signOutButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (AppPreferences(this).cookies == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            this.finish()
        } else {
            signOut()
        }
    }

    private fun signOut() {
        signOutButton = findViewById(R.id.sign_out_button)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.CLIENT_ID).requestEmail().build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        signOutButton?.setOnClickListener {
            mGoogleSignInClient.signOut().addOnCompleteListener(this) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                this.finish()
            }

            val mainViewModel by viewModels<MainViewModel>()
            try {
                mainViewModel.apiCaller.observe(this) {
                    if (it != null) {
                        Log.e("MainActivity", "logout")
                    }
                }
                // Signed in successfully, show authenticated UI.

            } catch (e: ApiException) {
                Log.w("exp handleSignIn", "logoutResult:failed code=" + e.statusCode)
            }
        }
    }
}