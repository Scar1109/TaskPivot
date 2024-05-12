package com.example.taskpivot.modules

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import com.example.taskpivot.R
import com.example.taskpivot.databinding.ActivityAddNewScreenBinding
import com.example.taskpivot.databinding.ActivityHomeScreenBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class AddNewScreen : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddNewScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.selectedItemId = R.id.page_2

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> {
                    val intent = Intent(this, HomeScreen::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    true
                }
                R.id.page_2 -> {
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

        binding.dateTxt.text = "YYYY-MM-DD"

        binding.buttonDate.setOnClickListener{
            showDatePicker()
        }
    }

    private fun showDatePicker() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.timeInMillis = selection
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val selectedDate = dateFormat.format(calendar.time)
            binding.dateTxt.text = selectedDate
            binding.dateTxt.setTextColor(ContextCompat.getColor(this, R.color.input_hint_color))

        }


            datePicker.show(supportFragmentManager, "datePicker")
    }
}