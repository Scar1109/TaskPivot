package com.example.taskpivot.modules

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.example.taskpivot.R
import com.example.taskpivot.databinding.ActivityAddNewScreenBinding
import com.example.taskpivot.databinding.ActivityPriorityScreenBinding

class PriorityScreen : AppCompatActivity() {

    private lateinit var binding: ActivityPriorityScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPriorityScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.selectedItemId = R.id.page_3

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> {
                    val intent = Intent(this, HomeScreen::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    true
                }
                R.id.page_2 -> {
                    val intent = Intent(this, AddNewScreen::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    true
                }
                R.id.page_3 -> {
                    true
                }
                else -> false
            } }
    }
}