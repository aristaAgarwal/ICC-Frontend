package com.example.fintech.UI.fragments

import android.app.PendingIntent
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentManager
import com.example.fintech.R
import com.example.fintech.databinding.FragmentOtpLoginBinding
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest
import com.google.android.gms.auth.api.identity.Identity

class OtpLogin : Fragment() {

    var binding: FragmentOtpLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOtpLoginBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding!!.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPhoneNo()

    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.back -> {

            }
        }
    }

    fun getPhoneNo() {
        val request: GetPhoneNumberHintIntentRequest =
            GetPhoneNumberHintIntentRequest.builder().build()
        val phoneNumberHintIntentResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
                try {
                    val phoneNumber = Identity.getSignInClient(requireContext())
                        .getPhoneNumberFromIntent(result.data)
                    Log.e("phine number", phoneNumber)
                } catch (e: Exception) {
                    Log.e("Login Activity phn", "Phone Number Hint failed")
                }
            }
        Identity.getSignInClient(requireContext())
            .getPhoneNumberHintIntent(request)
            .addOnSuccessListener { result: PendingIntent ->
                try {
                    phoneNumberHintIntentResultLauncher.launch(
                        IntentSenderRequest.Builder(result).build()
                    )
                } catch (e: Exception) {
                    Log.e("Login Activity phn", "Launching the PendingIntent failed")
                }
            }
            .addOnFailureListener {
                Log.e("Login Activity phn", "Phone Number Hint failed")
            }

    }

}