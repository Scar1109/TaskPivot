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
import com.example.taskpivot.databinding.ActivityPriorityScreenBinding
import com.example.taskpivot.popups.PriorityDeleteConformationModel

class PriorityScreen : AppCompatActivity(), TaskAdapter.OnDeleteClickListener, TaskAdapter.OnPriorityClickListener {

    private lateinit var binding: ActivityPriorityScreenBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPriorityScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskViewModel = TaskViewModel(application)

        // Retrieve tasks
        val tasks = taskViewModel.getAllTasks()

        // Update UI with retrieved tasks
        updateUI(tasks)

        //empty add new btn
        binding.emptyAddnewBtn.setOnClickListener {
            val intent = Intent(this, AddNewScreen::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        // Set up bottom navigation
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
            }
        }
    }

    private fun updateUI(tasks: List<Task>) {

        val priorityTasks = tasks.filter { it.taskPriority }

        // Initialize RecyclerView
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Pass the activity as a listener to the adapter
        taskAdapter = TaskAdapter(priorityTasks, this, this)
        recyclerView.adapter = taskAdapter

        // Set RecyclerView visibility
        if (priorityTasks.isEmpty()) {
            binding.emptyView.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        } else {
            binding.emptyView.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onDeleteClick(taskId: Int) {
        val conformDeleteModel = PriorityDeleteConformationModel.newInstance(taskId)
        conformDeleteModel.show(supportFragmentManager, PriorityDeleteConformationModel.TAG)
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
            val priorityTasks = updatedTasks.filter { it.taskPriority }
            taskAdapter.updateTasks(priorityTasks)
            if(priorityTasks.isEmpty()){
                updateUI(priorityTasks)
            }
        } else {
            // Database update failed
            Toast.makeText(this, "Failed to update priority", Toast.LENGTH_SHORT).show()
        }
    }
}
