package com.example.fintech.UI.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fintech.model.Phone
import com.example.fintech.model.VerifyOtpDO
import com.example.fintech.UI.activities.MainActivity
import com.example.fintech.constants.AppPreferences
import com.example.fintech.databinding.FragmentOtpFetchBinding
import com.example.fintech.viewModel.MainViewModel
import com.google.android.gms.common.api.ApiException


class OtpFetch : Fragment() {

    var binding: FragmentOtpFetchBinding? = null
    @SuppressLint("SetTextI18n")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOtpFetchBinding.inflate(inflater, container, false)
        binding?.phoneNo?.text = arguments?.getString("inputText")
        binding?.signInButton?.setOnClickListener {
            authenticate()
        }
        return binding!!.root
    }

    fun authenticate(){
        val mainViewModel by viewModels<MainViewModel>()
        try {
            val phoneNumber: String = binding?.phoneNo?.text.toString().takeLast(10)
            val otpText: String = binding?.otp?.text.toString()

            val verifyOtpDO = VerifyOtpDO(phoneNumber, otpText)
            mainViewModel.otpVerification(verifyOtpDO)
            mainViewModel.apiCaller.observe(viewLifecycleOwner){
                if (mainViewModel.cookies != null) {
                    AppPreferences(context).cookies = mainViewModel.cookies
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            // Signed in successfully, show authenticated UI.

        } catch (e: ApiException) {
            Log.w("exp handleSignIn", "signInResult:failed code=" + e.statusCode)
        }
    }
}