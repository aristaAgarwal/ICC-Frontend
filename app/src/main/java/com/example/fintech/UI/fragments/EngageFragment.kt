package com.example.fintech.UI.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fintech.UI.activities.SpinWheelActivity
import com.example.fintech.databinding.FragmentEngageBinding

class EngageFragment : Fragment() {

    var binding: FragmentEngageBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEngageBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    fun init(){

        binding?.cardItemLayout?.setOnClickListener {
            val intent = Intent(context, SpinWheelActivity::class.java)
            startActivity(intent)
        }
    }
}