package com.example.taskpivot.modules

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.example.taskpivot.R
import com.example.taskpivot.databinding.ActivityHomeScreenBinding
import com.example.taskpivot.databinding.ActivityOnboardingScreen01Binding

class HomeScreen : AppCompatActivity() {

    private lateinit var binding: ActivityHomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        enableEdgeToEdge()

        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.selectedItemId = R.id.page_1

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> {
                    true
                }
                R.id.page_2 -> {
                    val intent = Intent(this, AddNewScreen::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    true
                }
                R.id.page_3 -> {
                    val intent = Intent(this, PriorityScreen::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    true
                }
                else -> false
            } }

    }
}