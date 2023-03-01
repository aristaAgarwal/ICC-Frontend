package com.example.fintech.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.fintech.UI.fragments.HomeFragment
import com.example.fintech.UI.fragments.MatchesFragment
import com.example.fintech.UI.fragments.NewsFragment
import com.example.fintech.UI.fragments.VideosFragment

class ViewPagerAdapter(fragment: Fragment,var fragments: List<Fragment>) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment {

        when (position) {
            0 -> return fragments[position]
            1 -> return fragments[position]
            2 -> return fragments[position]
        }
        Log.e("Adapter", position.toString())
        return fragments[position]
    }
}