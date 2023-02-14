package com.example.fintech.UI.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.fintech.BuildConfig
import com.example.fintech.Model.IdToken
import com.example.fintech.R
import com.example.fintech.UI.fragments.OtpLogin
import com.example.fintech.databinding.ActivityLoginBinding
import com.example.fintech.viewModel.MainViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.CLIENT_ID).requestEmail().build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val getResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(it.data)
                    handleSignInResult(task)
                }
            }

        binding.signInButton.setOnClickListener {
            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            getResult.launch(signInIntent)
        }

    }

    fun onClick(view: View) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.frameLayout, OtpLogin())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }


    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        val mainViewModel by viewModels<MainViewModel>()
        try {
            val account = completedTask.getResult(ApiException::class.java)
            var id: String? = account.idToken
            Log.e("idToken: ", id.toString())

            mainViewModel.idToken = IdToken(id!!)
            Log.e("idTokenInLoginActivity", mainViewModel.idToken.toString())
            mainViewModel.authenticate(mainViewModel.idToken!!)
            mainViewModel.apiCaller.observe(this) {
                if (it != null) {
                    updateUI(account)
                }
            }
            // Signed in successfully, show authenticated UI.

        } catch (e: ApiException) {
            Log.w("exp handleSignIn", "signInResult:failed code=" + e.statusCode)
            updateUI(null)
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account == null) {
            Toast.makeText(this, "Not signed in", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this, "SIGNED IN", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}