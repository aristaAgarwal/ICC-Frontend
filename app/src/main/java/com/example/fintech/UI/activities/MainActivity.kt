package com.example.fintech.UI.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.fintech.BuildConfig
import com.example.fintech.R
import com.example.fintech.UI.fragments.EngageFragment
import com.example.fintech.UI.fragments.HomeFragment
import com.example.fintech.UI.fragments.ShopFragment
import com.example.fintech.UI.fragments.StatsFragment
import com.example.fintech.constants.AppPreferences
import com.example.fintech.databinding.ActivityMainBinding
import com.example.fintech.viewModel.MainViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerNav: DrawerLayout = binding.myDrawerLayout
        val displayPic: CardView = binding.drawerOpener

        displayPic.setOnClickListener {
            drawerNav.openDrawer(GravityCompat.START)
        }

        binding.bottomNavigationView.itemIconTintList = null
        setCurrentFragment(HomeFragment())
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> setCurrentFragment(HomeFragment())
                R.id.shop -> setCurrentFragment(ShopFragment())
                R.id.engage -> setCurrentFragment(EngageFragment())
                R.id.stats -> setCurrentFragment(StatsFragment())
            }
            true
        }


        if (AppPreferences(this).cookies == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            this.finish()
        } else {
            signOut()
        }
    }

    private fun signOut() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.CLIENT_ID).requestEmail().build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.navigationView.menu.findItem(R.id.nav_logout).setOnMenuItemClickListener {
            AppPreferences(this).cookies = null
            val mainViewModel by viewModels<MainViewModel>()
            mainViewModel.logout(AppPreferences(this).cookies)
            mainViewModel.apiCaller.observe(this) {
                if (it != null) {
                    Log.e("MainActivity", "logout")
                    mGoogleSignInClient.signOut().addOnCompleteListener(this) {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        this.finish()
                    }
                }
            }
            false
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }

//    fun onNavigationItemSelected(item: MenuItem): Boolean {
//        // Handle navigation view item clicks here.
//        val id: Int = item.getItemId()
//        if (id == com.example.fintech.R.id.nav_logout) {
//            // Handle the camera action
//        }
//        val drawer = binding.myDrawerLayout
//        drawer.closeDrawer(GravityCompat.START)
//        return true
//    }
}