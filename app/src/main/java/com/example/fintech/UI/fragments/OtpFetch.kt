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
import com.example.fintech.Model.Phone
import com.example.fintech.Model.VerifyOtpDO
import com.example.fintech.UI.activities.MainActivity
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
        var inputText = arguments?.getString("inputText")
        Log.e("phone", inputText!!)
        binding?.phoneNo?.text = inputText
        val otpEditText = binding?.otp
        binding?.signInButton?.setOnClickListener {
            val mainViewModel by viewModels<MainViewModel>()
            try {
                val phoneNumber: String = binding?.phoneNo?.text.toString().takeLast(10)
                val otpText: String = otpEditText?.text.toString()

                val verifyOtpDO = VerifyOtpDO(phoneNumber, otpText)
                mainViewModel.otpVerification(verifyOtpDO)
                mainViewModel.verifyOtpApiCaller.observe(viewLifecycleOwner){
                    if (mainViewModel.cookies != null) {
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
                // Signed in successfully, show authenticated UI.

            } catch (e: ApiException) {
                Log.w("exp handleSignIn", "signInResult:failed code=" + e.statusCode)
            }
        }
        return binding!!.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        val mainViewModel by viewModels<MainViewModel>()
        try {
            var phoneNumber: String? = binding?.phoneNo?.text.toString().takeLast(10)
            Log.e("phone OtpFetch: ", phoneNumber!!)

            val phone = Phone(phoneNumber)
            Log.e("phoneNumberAPI", phone.toString())
            mainViewModel.otpAuthentication(phone)
            mainViewModel.otpApiCaller.observe(this) {
                if (it != null) {
                    Log.e("OtpFetch", "otp sent")
                }
            }
            // Signed in successfully, show authenticated UI.

        } catch (e: ApiException) {
            Log.w("exp handleSignIn", "signInResult:failed code=" + e.statusCode)
        }
    }
}