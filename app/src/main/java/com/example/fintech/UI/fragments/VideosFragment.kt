package com.example.fintech.UI.fragments

import android.media.MediaPlayer.OnCompletionListener
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fintech.constants.AppPreferences
import com.example.fintech.databinding.FragmentVideosBinding
import com.example.fintech.viewModel.MainViewModel


class VideosFragment : Fragment() {
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

    fun getVideos(cookies: String, type: String){
        val mainViewModel by viewModels<MainViewModel>()
        mainViewModel.getTutorials(cookies, type)
        mainViewModel.videosApiCaller.observe(
            viewLifecycleOwner
        ) {
            if (it.code == 200) {
                tutorialUri = Uri.parse(it.data[0].src)
                var videoView = binding?.videoView
                videoView?.setVideoURI(tutorialUri)

                // on below line we are creating variable
                // for media controller and initializing it.
                val mediaController = MediaController(context)

                // on below line we are setting anchor
                // view for our media controller.
                mediaController.setAnchorView(videoView)

                // on below line we are setting media player
                // for our media controller.
//                mediaController.setMediaPlayer(videoView)

                // on below line we are setting media
                // controller for our video view.
//                videoView?.setMediaController(mediaController)
                videoView?.setOnCompletionListener(OnCompletionListener {
                        videoView?.start()
                })
                // on below line we are
                // simply starting our video view.
            }
        }
    }
}