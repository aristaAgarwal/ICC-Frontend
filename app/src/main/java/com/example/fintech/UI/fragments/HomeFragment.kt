package com.example.fintech.UI.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.fintech.R
import com.example.fintech.adapter.ViewPagerAdapter
import com.example.fintech.databinding.FragmentHomeBinding
import com.example.fintech.databinding.FragmentMatchesBinding
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() {

    var binding: FragmentHomeBinding? = null
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var fragments = listOf(MatchesFragment(), NewsFragment(), VideosFragment())
        binding?.pager?.adapter = ViewPagerAdapter(this, fragments)

        TabLayoutMediator(
            binding!!.tabLayout, binding!!.pager
        ) { tab, position ->
            tab.text = when (position) {
                0 -> "Matches"
                1 -> "News"
                2 -> "Videos"
                else -> {
                    ""
                }
            }
        }.attach()
    }

    fun setPageViewAdapter() {

    }
}