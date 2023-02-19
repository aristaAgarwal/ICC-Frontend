package com.example.fintech.UI.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.fintech.R
import com.example.fintech.databinding.FragmentOtpFetchBinding
import com.example.fintech.databinding.FragmentOtpLoginBinding

class OtpFetch : Fragment() {

    lateinit var binding: FragmentOtpFetchBinding
    @SuppressLint("SetTextI18n")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOtpFetchBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        binding.otp.setText("0293")

    }
}