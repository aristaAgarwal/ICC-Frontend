package com.example.fintech.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.fintech.UI.fragments.HomeFragment
import com.example.fintech.UI.fragments.MatchesFragment
import com.example.fintech.UI.fragments.NewsFragment
import com.example.fintech.UI.fragments.VideosFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {

        when (position) {
            0 -> return MatchesFragment()
            1 -> return NewsFragment()
            2 -> return VideosFragment()
        }
        Log.e("Adapter", position.toString())
        return HomeFragment()
    }
}