package com.example.taskpivot.modules

import Task
import TaskViewModel
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

class HomeScreen : AppCompatActivity(), TaskAdapter.OnDeleteClickListener, TaskAdapter.OnPriorityClickListener, TaskAdapter.OnTaskClickListener {

    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private var tasks: List<Task> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskViewModel = TaskViewModel(application)

        // Retrieve tasks
        tasks = taskViewModel.getAllTasks()

        // Pass the activity as a listener to the adapter
        updateUI(tasks)

        // Set up search functionality
        binding.searchTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                filterTasks(s.toString())
            }
        })

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
        taskAdapter = TaskAdapter(tasks, this, this, this)
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

    override fun onPriorityClick(taskId: Int, currentPriority: Boolean) {
        // Update priority in the database
        val updatedRows = taskViewModel.updateTaskPriority(taskId, currentPriority)
        if (updatedRows > 0) {
            // Database update successful
            // Refresh UI
            val updatedTasks = taskViewModel.getAllTasks()
            taskAdapter.updateTasks(updatedTasks)
        } else {
            // Database update failed
            Toast.makeText(this, "Failed to update priority", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onTaskClick(taskId: Int) {
        // Show a Toast message with the task ID
        Toast.makeText(this, "Task ID: $taskId", Toast.LENGTH_SHORT).show()
    }

    private fun filterTasks(query: String) {
        val filteredTasks = tasks.filter {
            it.taskTitle.contains(query, ignoreCase = true) || it.taskDescription.contains(query, ignoreCase = true)
        }
        taskAdapter.updateTasks(filteredTasks)
    }
}
