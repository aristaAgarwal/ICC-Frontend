package com.example.fintech

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.fintech.Model.IdToken
import com.example.fintech.viewModel.MainViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class LoginActivity : AppCompatActivity() {
    private var signInButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        signInButton = findViewById(R.id.sign_in_button)

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

        signInButton?.setOnClickListener {
            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            getResult.launch(signInIntent)
        }

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

            val idToken = IdToken(id!!)
            id = null
            mainViewModel.authenticate(idToken)
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
            this.finish()
        }
    }
}