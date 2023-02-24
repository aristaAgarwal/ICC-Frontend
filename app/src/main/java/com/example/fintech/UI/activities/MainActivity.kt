package com.example.fintech.UI.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fintech.R
import com.example.fintech.UI.fragments.EngageFragment
import com.example.fintech.UI.fragments.HomeFragment
import com.example.fintech.UI.fragments.ShopFragment
import com.example.fintech.UI.fragments.StatsFragment
import com.example.fintech.constants.AppPreferences
import com.example.fintech.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.itemIconTintList = null
        setCurrentFragment(HomeFragment())
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->setCurrentFragment(HomeFragment())
                R.id.shop->setCurrentFragment(ShopFragment())
                R.id.engage->setCurrentFragment(EngageFragment())
                R.id.stats->setCurrentFragment(StatsFragment())
            }
            true
        }

        if (AppPreferences(this).cookies == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            this.finish()
        }

    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}