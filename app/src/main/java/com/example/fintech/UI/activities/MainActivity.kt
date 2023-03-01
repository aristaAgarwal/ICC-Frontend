package com.example.fintech.UI.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
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

//        onNavigationItemSelected(binding.navigationView.menu.findItem(R.id.nav_account))

        binding.navigationView.menu.findItem(R.id.nav_account).setOnMenuItemClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            true
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
        }
        else{
            getUserInfo()
        }
    }

    fun getUserInfo(){
        val mainViewModel by viewModels<MainViewModel>()
        mainViewModel.getUserInfo(AppPreferences(this).cookies)
        mainViewModel.apiCaller.observe(
            this
        ){
            if(it != null){
                loadImage(it.data.profile_img_url, binding.profilePic)
            }
        }
    }

    fun loadImage(imageUrl: String, imageView: ImageView) {
        Glide.with(this).asBitmap().load(imageUrl).into(object : CustomTarget<Bitmap?>() {
            override fun onResourceReady(
                resource: Bitmap, transition: Transition<in Bitmap?>?
            ) {
                imageView.setImageBitmap(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }

//    fun onNavigationItemSelected(item: MenuItem): Boolean {
//        // Handle navigation view item clicks here.
//        val id: Int = item.getItemId()
//        if (id == R.id.nav_account) {
//            val intent = Intent(this, ProfileActivity::class.java)
//            startActivity(intent)
//        }
//        val drawer = binding.myDrawerLayout
//        drawer.closeDrawer(GravityCompat.START)
//        return true
//    }
}