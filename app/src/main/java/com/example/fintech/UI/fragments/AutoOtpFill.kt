package com.example.fintech.UI.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fintech.R
import com.example.fintech.databinding.FragmentAutoOtpFillBinding
import com.example.fintech.databinding.FragmentOtpLoginBinding

class AutoOtpFill : Fragment() {

    var binding: FragmentAutoOtpFillBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAutoOtpFillBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding!!.root
    }
}