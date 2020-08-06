package com.shehzabahammad.workout_timer.splash.screen.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.shehzabahammad.workout_timer.R
import com.shehzabahammad.workout_timer.home.screen.activity.HomeActivity

class SplashScreenActivity : AppCompatActivity() {

    private var splashTimeOut = 3000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
            overridePendingTransition(R.anim.slide_out_up, R.anim.slide_in_up)
        }, splashTimeOut)
    }
}