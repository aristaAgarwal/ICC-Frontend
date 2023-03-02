package com.example.fintech.UI.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fintech.R
import com.example.fintech.VideoActivity
import com.example.fintech.databinding.ActivityPlayerBinding
import com.example.fintech.databinding.FragmentVideosBinding

class VideosFragment : Fragment() {

    var binding: FragmentVideosBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVideosBinding.inflate(inflater, container, false)
        binding!!.videoButton.setOnClickListener {
            val intent = Intent(activity, VideoActivity::class.java)
            startActivity(intent)
        }

        return binding!!.root
    }

}