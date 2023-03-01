package com.example.fintech.UI.activities

import android.graphics.drawable.Animatable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import com.example.fintech.databinding.ActivitySpinWheelBinding

class SpinWheelActivity : AppCompatActivity(), Animation.AnimationListener {
    lateinit var binding: ActivitySpinWheelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpinWheelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init() {
        binding.spin.setOnClickListener {
            startSpinner()
        }
    }

    fun startSpinner() {
        val rotateAnim = RotateAnimation(
            0f, 3600f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotateAnim.interpolator = AccelerateDecelerateInterpolator()
        rotateAnim.repeatCount = 0
        rotateAnim.duration = (3000..5000).random().toLong()
        rotateAnim.setAnimationListener(this)
        rotateAnim.fillAfter = true
        binding.wheel.startAnimation(rotateAnim)
    }

    override fun onAnimationStart(animation: Animation?) {
    }

    override fun onAnimationEnd(animation: Animation?) {
    }

    override fun onAnimationRepeat(animation: Animation?) {

    }
}