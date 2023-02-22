package com.example.fintech.UI.fragments

import android.app.PendingIntent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.example.fintech.Comminucator.PhoneCommunicator
import com.example.fintech.R
import com.example.fintech.databinding.FragmentFetchNumberBinding
import com.example.fintech.model.Phone
import com.example.fintech.viewModel.MainViewModel
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException

class FetchNumber : Fragment(), PhoneCommunicator {

    var binding: FragmentFetchNumberBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFetchNumberBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPhoneNo()
    }

    fun getPhoneNo() {
        val request: GetPhoneNumberHintIntentRequest =
            GetPhoneNumberHintIntentRequest.builder().build()
        val phoneNumberHintIntentResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
                try {
                    val phoneNumber = Identity.getSignInClient(requireContext())
                        .getPhoneNumberFromIntent(result.data)
                    binding?.phoneNumber?.setText(phoneNumber)
                    binding?.submitNo?.setOnClickListener {
                        getOtp()
                    }
                    Log.e("phone number", phoneNumber)
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

    private fun getOtp() {

        val mainViewModel by viewModels<MainViewModel>()
        try {
            val phoneNumber: String = binding?.phoneNumber?.text.toString().takeLast(10)
            Log.e("phone OtpFetch: ", phoneNumber)

            val phone = Phone(phoneNumber)
            mainViewModel.otpAuthentication(phone)
            mainViewModel.apiCaller.observe(viewLifecycleOwner) {
                if (it != null) {
                    passPhone(binding?.phoneNumber?.text.toString())
                }
            }

        } catch (e: ApiException) {
            Log.w("exp handleSignIn", "signInResult:failed code=" + e.statusCode)
        }
    }
    override fun passPhone(editTextInput: String) {
        val bundle = Bundle()
        bundle.putString("inputText", editTextInput)
        val transaction = parentFragmentManager.beginTransaction()
        OtpFetch().arguments = bundle
        transaction.replace(R.id.frameLayout, OtpFetch())
        transaction.addToBackStack(null)
        transaction.commit()
    }

}