package com.example.fintech.UI.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fintech.model.VerifyOtpDO
import com.example.fintech.UI.activities.MainActivity
import com.example.fintech.constants.AppPreferences
import com.example.fintech.databinding.FragmentOtpFetchBinding
import com.example.fintech.viewModel.MainViewModel
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.referral_code_layout.view.*


class OtpFetch : Fragment() {

    var binding: FragmentOtpFetchBinding? = null
    @SuppressLint("SetTextI18n", "LongLogTag")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOtpFetchBinding.inflate(inflater, container, false)
        arguments?.getString("inputText")?.let { Log.e("Fetch Otp Phone Input Pass", it) }
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
            Log.e("Phone OtpFetch", phoneNumber)
            Log.e("Otp OtpFetch", otpText)

            val verifyOtpDO = VerifyOtpDO(phoneNumber, otpText)
            mainViewModel.otpVerification(verifyOtpDO)
            mainViewModel.apiCaller.observe(viewLifecycleOwner){
                if (mainViewModel.cookies != null) {
                    AppPreferences(context).cookies = mainViewModel.cookies
                    if (AppPreferences(context).firstLaunch) {
                        AppPreferences(context).firstLaunch = false
                        showReferralFlow()
                    } else{
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }
                }
            }
            // Signed in successfully, show authenticated UI.

        } catch (e: ApiException) {
            Log.w("exp handleSignIn", "signInResult:failed code=" + e.statusCode)
        }
    }


    fun showReferralFlow() {
        setLayout(binding?.referralLayout, true)
        val code: String = binding?.referralCode?.referralCode?.text.toString()
        binding?.referralLayout!!.continue_button.setCardBackgroundColor(Color.BLACK)
        binding?.referralLayout!!.continue_button.setOnClickListener {
            setLayout(binding?.referralLayout, false)
            Toast.makeText(context, "Hurrayy!!\nYou received 100 coins", Toast.LENGTH_SHORT).show()
            setLayout(binding?.referralLayoutSuccess, true)
        }
        binding?.referralLayout!!.skip_button.setOnClickListener {
            setLayout(binding?.referralLayout, false)
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        binding?.referralCodeSuccess!!.continueButton.setOnClickListener {
            setLayout(binding?.referralLayoutSuccess, false)
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }


    fun setLayout(layout: RelativeLayout?, b: Boolean) {

        layout?.isFocusable = b
        layout?.isVisible = b
        layout?.isClickable = b
    }
}