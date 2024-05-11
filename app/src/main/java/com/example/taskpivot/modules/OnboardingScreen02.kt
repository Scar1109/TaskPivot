package com.example.taskpivot.modules

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.example.taskpivot.R
import com.example.taskpivot.databinding.ActivityOnboardingScreen01Binding
import com.example.taskpivot.databinding.ActivityOnboardingScreen02Binding

class OnboardingScreen02 : AppCompatActivity() {

    private lateinit var binding : ActivityOnboardingScreen02Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityOnboardingScreen02Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.skipText01.setOnClickListener{
            val intent2 = Intent( this, HomeScreen::class.java)
            startActivity(intent2)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        binding.onboardingbtn01.setOnClickListener {
            val intent1 = Intent(this, HomeScreen::class.java)
            startActivity(intent1)
        }

    }
}