package com.example.fintech.UI.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.fintech.BuildConfig
import com.example.fintech.constants.AppPreferences
import com.example.fintech.databinding.ActivityProfileBinding
import com.example.fintech.viewModel.MainViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding

    //    var spinner : Spinner? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setCountry()
        signOut()
        init()
        getUserInfo()
    }

    fun init(){
        binding.back.setOnClickListener{
            this.finish()
        }
    }
    //    private fun setCountry(){
//        spinner = binding.countrySpinner
//        val countries = arrayOf("India", "Australia", "England", "New Zealand")
//        val countryAdapter = ArrayAdapter<CharSequence>(this, R.layout.spinner_text, countries)
//        countryAdapter.setDropDownViewResource(R.layout.spinner_dropdown)
//        spinner?.adapter = countryAdapter
//    }

    private fun signOut() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.CLIENT_ID).requestEmail().build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.logout.setOnClickListener {
            val mainViewModel by viewModels<MainViewModel>()
            mainViewModel.logout(AppPreferences(this).cookies)
            mainViewModel.logoutApiCaller.observe(this) {
                if (it != null) {
                    AppPreferences(this).cookies = null
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

    fun getUserInfo(){
        val mainViewModel by viewModels<MainViewModel>()
        mainViewModel.getUserInfo(AppPreferences(this).cookies)
        mainViewModel.apiCaller.observe(
            this
        ){
            if(it != null){
                loadImage(it.data!!.profile_img_url, binding.profilePic)
                binding.name.text = it.data.name
                binding.emailId.text = it.data.email
            }
        }
    }

    fun loadImage(imageUrl: String, imageView: ImageView) {
        Glide.with(this).asBitmap().load(imageUrl).into(object : CustomTarget<Bitmap?>() {
            override fun onResourceReady(
                resource: Bitmap, transition: Transition<in Bitmap?>?
            ) {
                imageView.setImageBitmap(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    }

}