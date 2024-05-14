package com.example.taskpivot

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import com.example.taskpivot.modules.HomeScreen
import com.example.taskpivot.modules.OnboardingScreen01

class MainActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        handler.postDelayed({
            if (isUserLoggedInOnce()) {
                // User has logged in before, navigate to HomeScreen
                startActivity(Intent(this, HomeScreen::class.java))
            } else {
                // User has not logged in before, show OnboardingScreen
                setUserLoggedInOnce()
                startActivity(Intent(this, OnboardingScreen01::class.java))

            }
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }, 2500L)
    }

    private fun isUserLoggedInOnce(): Boolean {
        val sharedPreferences = getSharedPreferences("taskPivotPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("userLoggedInOnce", false)
    }

    private fun setUserLoggedInOnce() {
        val sharedPreferences = getSharedPreferences("taskPivotPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("userLoggedInOnce", true)
        editor.apply()
    }
}