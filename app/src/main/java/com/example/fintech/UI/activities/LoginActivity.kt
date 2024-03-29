package com.example.fintech.UI.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.fintech.BuildConfig
import com.example.fintech.R
import com.example.fintech.UI.fragments.FetchNumber
import com.example.fintech.constants.AppPreferences
import com.example.fintech.databinding.ActivityLoginBinding
import com.example.fintech.model.IdToken
import com.example.fintech.viewModel.MainViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.referral_code_layout.view.*

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    var flag: Boolean = false
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
        when (view.id) {
            R.id.back -> {
                val fragmentManager = supportFragmentManager
                val fragment = fragmentManager.findFragmentById(R.id.frameLayout)
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.remove(fragment!!)
                fragmentTransaction.commit()
                fragmentManager.popBackStack()
            }
            R.id.otp_sign_in_btn -> {
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.add(R.id.frameLayout, FetchNumber())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
            R.id.back_btn -> {
                val fragmentManager = supportFragmentManager
                val fragment = fragmentManager.findFragmentById(R.id.frameLayout)
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.remove(fragment!!)
                fragmentTransaction.commit()
                fragmentManager.popBackStack()
            }
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
            mainViewModel.authenticate(idToken)
            mainViewModel.apiCaller.observe(this) {
                if (it != null) {
                    Log.e("loginActivity", it.data.toString().contains("name").toString())
                    Log.e(
                        "loginActivity",
                        it.data.toString().substringAfter("name=").substringBefore(",")
                    )
                    AppPreferences(this).idToken = id
                    AppPreferences(this).cookies = mainViewModel.cookies
                    updateUI(account)
                }
            }
            // Signed in successfully, show authenticated UI.

        } catch (e: ApiException) {
            Log.w("exp handleSignIn", "signInResult:failed code=" + e.statusCode)
            updateUI(null)
        }
    }

    fun updateUI(account: GoogleSignInAccount?) {
        if (account == null) {
            Toast.makeText(this, "Not signed in", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this, "SIGNED IN", Toast.LENGTH_SHORT).show()
            if (AppPreferences(this).firstLaunch) {
                showReferralFlow()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                this.finish()
            }
        }
    }

    fun showReferralFlow() {
        if (!flag)
            setLayout(binding.referralLayout, true)


        binding.referralCode.skipButton.setOnClickListener {
            setLayout(binding.referralLayout, false)
            AppPreferences(this).firstLaunch = false
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        binding.referralCode.referralCode.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                val code: String = binding.referralCode.referralCode.text.toString()
                if (code.length == 6) {
                    setListeners(code)
                    binding.referralCode.errorCode.isVisible = false
                } else {
                    binding.referralCode.errorCode.isVisible = true
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int, count: Int
            ) {
            }
        })

    }

    fun setListeners(code: String) {
        binding.referralCode.continueButton.setOnClickListener {

            window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
            );
            val viewModel by viewModels<MainViewModel>()
            viewModel.checkReferral(code)
            flag = true
            viewModel.apiCaller.observe(
                this
            ) {
                if (it != null) {
                    if (it.data != null) {
                        binding.referralCode.errorCode.isVisible = false
                        setLayout(binding.referralLayout, false)
                        setLayout(binding.referralLayoutSuccess, true)
                        showSuccessLayout()
                    } else {
                        binding.referralCode.errorCode.isVisible = true
                    }
                }
            }
        }

    }
    fun View.hideSoftInput(){
        val inputMethodManager = this@LoginActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken,0)
    }
    fun showSuccessLayout() {

        binding.referralCodeSuccess.continueButton.setOnClickListener {
            Log.e("Success", "I m clicked")
            Toast.makeText(this, "Hurrayy!!\nYou received 100 coins", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            AppPreferences(this).firstLaunch = false
            startActivity(intent)
            setLayout(binding.referralLayoutSuccess, false)
            this.finish()
        }
    }

    fun setLayout(layout: RelativeLayout, b: Boolean) {

        layout.isFocusable = b
        layout.isVisible = b
        layout.isClickable = b
    }

}