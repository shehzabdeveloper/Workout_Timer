package com.shehzabahammad.workout_timer.home.screen.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.shehzabahammad.workout_timer.R
import kotlinx.android.synthetic.main.toolbar.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        ivSettings.setOnClickListener {
            Toast.makeText(this, "Clicked Settings", Toast.LENGTH_SHORT).show()
        }
    }

}