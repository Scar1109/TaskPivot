package com.example.taskpivot.modules

import TaskViewModel
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.example.taskpivot.R
import com.example.taskpivot.databinding.ActivityHomeScreenBinding
import com.example.taskpivot.databinding.ActivityTaskViewScreenBinding
import com.example.taskpivot.popups.DeleteConformationModel
import com.example.taskpivot.popups.EditTaskModel

class TaskViewScreen : AppCompatActivity() {

    private lateinit var binding: ActivityTaskViewScreenBinding
    private lateinit var taskViewModel: TaskViewModel
    private var taskId : Int = -1
    private var priority : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        taskId = intent.getIntExtra("taskId",-1)

        binding = ActivityTaskViewScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        binding.addnewBtn.setOnClickListener {
            val intent = Intent(this,AddNewScreen::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        taskViewModel = TaskViewModel(application)

        val task = taskViewModel.getTaskById(taskId)
        if (task != null) {
            // Display task details
            binding.taskTitle.text = task.taskTitle
            binding.taskDate.text = task.taskDate
            binding.taskDescription.text = task.taskDescription

            // Set priority button image based on task priority
            if (task.taskPriority) {
                binding.priorityBtn.setImageResource(R.drawable.liked_star)
            } else {
                binding.priorityBtn.setImageResource(R.drawable.unliked_star)
            }
        }

        binding.priorityBtn.setOnClickListener {
            priority = !priority
            if (priority) {
                binding.priorityBtn.setImageResource(R.drawable.liked_star)
            } else {
                binding.priorityBtn.setImageResource(R.drawable.unliked_star)
            }
            taskViewModel.updateTaskPriority(taskId,priority)
        }

        binding.deleteButton.setOnClickListener {
            val conformDeleteModel = DeleteConformationModel.newInstance(taskId)
            conformDeleteModel.show(supportFragmentManager, DeleteConformationModel.TAG)
        }

        binding.editBtn.setOnClickListener {
            val editTaskModel = EditTaskModel.newInstance(taskId)
            editTaskModel.show(supportFragmentManager, EditTaskModel.TAG)
        }

        //Bottom Navigation bar
        binding.bottomNavigation.selectedItemId = R.id.page_1

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
                    val intent = Intent(this, PriorityScreen::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    true
                }
                else -> false
            }
        }
    }
}