package com.example.fintech.UI.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fintech.databinding.FragmentStatsBinding


class StatsFragment : Fragment() {

    var binding: FragmentStatsBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding!!.root
    }
}