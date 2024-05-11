package com.example.taskpivot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import com.example.taskpivot.modules.OnboardingScreen01

class MainActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        handler.postDelayed({
            startActivity(Intent(this, OnboardingScreen01::class.java))
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }, 2500L)
    }
}