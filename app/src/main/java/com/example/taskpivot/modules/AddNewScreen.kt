package com.example.taskpivot.modules

import Task
import TaskViewModel
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.taskpivot.R
import com.example.taskpivot.databinding.ActivityAddNewScreenBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class AddNewScreen : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewScreenBinding
    private var taskDescription : String = ""
    private var taskTitle : String = ""
    private var selectedDate : String = ""
    private var priority : Boolean = false

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

        binding.priorityBtn.setOnClickListener {
            priority = !priority
            if(priority){
                binding.priorityBtn.setImageResource(R.drawable.liked_star)
            }else{
                binding.priorityBtn.setImageResource(R.drawable.unliked_star)
            }
        }


        binding.saveTaskBtn.setOnClickListener {

            val titleEditText: EditText = binding.titleInput.editText!!
            taskDescription = binding.taskDesciption.text.toString()
            taskTitle = titleEditText.text.toString()
            selectedDate = binding.dateTxt.text.toString()
            saveTask()
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

    private fun saveTask() {

        val task = Task(
            taskTitle = taskTitle,
            taskDescription = taskDescription,
            taskPriority = priority,
            taskDate = selectedDate
        )

        val taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        val result = taskViewModel.addTask(task)
        if (result != -1L) {
            Toast.makeText(this, "Task Created Successfully", Toast.LENGTH_SHORT).show()
            // Task saved successfully
            finish()
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }
}