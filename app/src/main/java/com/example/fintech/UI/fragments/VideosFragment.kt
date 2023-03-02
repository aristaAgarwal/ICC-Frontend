package com.example.fintech.UI.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fintech.R
import com.example.fintech.VideoActivity
import com.example.fintech.adapter.ProductCardAdapter
import com.example.fintech.adapter.VideoItemAdapter
import com.example.fintech.constants.AppPreferences
import com.example.fintech.databinding.FragmentVideosBinding
import com.example.fintech.model.VideosData
import com.example.fintech.viewModel.MainViewModel


class VideosFragment : Fragment(), VideoItemAdapter.AppLinkClick {
    var binding: FragmentVideosBinding? = null
    var tutorialUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVideosBinding.inflate(inflater, container, false)
        getVideos(AppPreferences(context).cookies, "tutorials")
        return binding!!.root
    }
    private fun getVideos(cookies: String, type: String){
        val mainViewModel by viewModels<MainViewModel>()
        mainViewModel.getTutorials(cookies, type)
        mainViewModel.videosApiCaller.observe(
            viewLifecycleOwner
        ) {
            if (it.code == 200) {
                Log.e("VideosFragment", it.data.toString())
                setTutorialsAdapter(it.data)
                setAustraliaTourAdapter(it.data)
                setPopularAdapter(it.data)
            }
        }
    }


    fun setTutorialsAdapter(data: List<VideosData>) {
        Log.e("Video Fragment", "In here")
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding?.tutorialsRv?.layoutManager = layoutManager
        binding?.tutorialsRv?.adapter = VideoItemAdapter(requireContext(), data, this, "first")
    }

    fun setAustraliaTourAdapter(data: List<VideosData>) {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding?.australiaTourRv?.layoutManager = layoutManager
        binding?.australiaTourRv?.adapter = VideoItemAdapter(requireContext(), data, this, "second")
    }

    fun setPopularAdapter(data: List<VideosData>) {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding?.popularRv?.layoutManager = layoutManager
        binding?.popularRv?.adapter = VideoItemAdapter(requireContext(), data, this, "third")
    }
    override fun onAppLinkClicked(url: String) {
        val intent = Intent(activity, VideoActivity::class.java)
        intent.putExtra("url", url)
        startActivity(intent)
    }
}