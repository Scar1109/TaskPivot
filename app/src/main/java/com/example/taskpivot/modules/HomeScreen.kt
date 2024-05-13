package com.example.taskpivot.modules

import Task
import TaskViewModel
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskpivot.R
import com.example.taskpivot.adapter.TaskAdapter
import com.example.taskpivot.databinding.ActivityHomeScreenBinding
import com.example.taskpivot.popups.DeleteConformationModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeScreen : AppCompatActivity(), TaskAdapter.OnDeleteClickListener {

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

        // Pass the activity as a listener to the adapter
        updateUI(tasks)

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
            }
        }

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
            currentHour in 16..17 -> R.drawable.evenining_sun
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

    private fun updateUI(tasks: List<Task>) {

        // Initialize RecyclerView
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Pass the activity as a listener to the adapter
        taskAdapter = TaskAdapter(tasks, this)
        recyclerView.adapter = taskAdapter

        if (tasks.isEmpty()) {
            binding.emptyView.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        } else {
            binding.emptyView.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onDeleteClick(taskId: Int) {
        val conformDeleteModel = DeleteConformationModel.newInstance(taskId)
        conformDeleteModel.show(supportFragmentManager, DeleteConformationModel.TAG)
    }

    // Function to handle task deletion and refresh data
    fun deleteTaskAndRefresh(taskId: Int) {
        // Delete task from ViewModel or Repository
        taskViewModel.deleteTask(taskId)

        // Get updated tasks from ViewModel or Repository
        val updatedTasks = taskViewModel.getAllTasks()
        updateUI(updatedTasks)
    }
}
