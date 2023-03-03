package com.example.fintech.UI.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.viewModels
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
    var flag: Boolean = false
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
        if(!flag){
            setLayout(binding?.referralLayout, true)
        }

        binding?.referralCode!!.referralCode.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                val code: String = binding!!.referralCode.referralCode.text.toString()
                if (code.length == 6) {
                    setListeners(code)
                    binding!!.referralCode.errorCode.isVisible = false
                } else {
                    binding!!.referralCode.errorCode.isVisible = true
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


        binding?.referralLayout?.skip_button?.setOnClickListener {
            setLayout(binding!!.referralLayout, false)
            AppPreferences(context).firstLaunch = false
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    fun setListeners(code: String) {
        binding!!.referralLayout.continue_button.setOnClickListener {
            val viewModel by viewModels<MainViewModel>()
            viewModel.checkReferral(code)
            flag = true
            viewModel.apiCaller.observe(
                this
            ) {
                if (it != null) {
                    if (it.data != null) {
                        binding?.referralCode!!.errorCode.isVisible = false
                        setLayout(binding!!.referralLayout, false)
                        setLayout(binding!!.referralLayoutSuccess, true)

                    } else {
                        binding!!.referralCode.errorCode.isVisible = true
                    }
                }
            }
        }

        binding?.referralCodeSuccess?.continueButton?.setOnClickListener {
            Toast.makeText(context, "Hurrayy!!\nYou received 100 coins", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, MainActivity::class.java)
            AppPreferences(context).firstLaunch = false
            startActivity(intent)
            activity?.finish()
            setLayout(binding!!.referralLayoutSuccess, false)
        }
    }


    fun setLayout(layout: RelativeLayout?, b: Boolean) {

        layout?.isFocusable = b
        layout?.isVisible = b
        layout?.isClickable = b
    }
}