package com.example.taskpivot.modules

import TaskViewModel
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskpivot.R
import com.example.taskpivot.adapter.TaskAdapter
import com.example.taskpivot.databinding.ActivityHomeScreenBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeScreen : AppCompatActivity() {

    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        enableEdgeToEdge()

        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskViewModel = TaskViewModel(application)

        // Retrieve tasks
        val tasks = taskViewModel.getAllTasks()

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(tasks)
        recyclerView.adapter = taskAdapter

        //Bottom Navigation bar
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

        //Greeting message and image
        val currentTime = Calendar.getInstance()
        val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)

        val greetingMessage = when (currentHour) {
            in 6..11 -> "Good morning"
            in 12..16 -> "Good afternoon"
            else -> "Good evening"
        }
        binding.greeting.text = greetingMessage

        // Format date
        val dateFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(currentTime.time)
        binding.date.text = formattedDate

        val imageResId = when {
            currentHour in 4..9 -> R.drawable.morining_sun
            currentHour in 10..15 -> R.drawable.noon_sun
            currentHour in 16 .. 17 -> R.drawable.evenining_sun
            else -> R.drawable.night_moon
        }

        binding.timeImg.setImageResource(imageResId)

        //empty add new btn
        binding.emptyAddnewBtn.setOnClickListener {
            val intent = Intent(this, AddNewScreen::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }




    }
}