package com.example.taskpivot.adapter

import Task
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskpivot.R

class TaskAdapter(private var tasks: List<Task>, private val listener: OnDeleteClickListener, private val priorityClickListener: OnPriorityClickListener, private val taskClickListener: OnTaskClickListener) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    // Define an interface for click listener
    interface OnDeleteClickListener {
        fun onDeleteClick(taskId: Int)
    }

    interface OnPriorityClickListener {
        fun onPriorityClick(taskId: Int, currentPriority: Boolean)
    }

    interface OnTaskClickListener {
        fun onTaskClick(taskId: Int)
    }


    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }


    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val titleTextView: TextView = itemView.findViewById(R.id.task_title)
        val descriptionTextView: TextView = itemView.findViewById(R.id.task_description)
        val dateTextView: TextView = itemView.findViewById(R.id.task_date)
        val deleteImageView: ImageView = itemView.findViewById(R.id.delete_button)
        val priorityImageView: ImageView = itemView.findViewById(R.id.priority_btn)

        init {
            itemView.setOnClickListener(this)
            deleteImageView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val task = tasks[position]
                // Call the interface method with the task ID
                taskClickListener.onTaskClick(task.id)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_task_item, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = tasks[position]
        holder.titleTextView.text = currentTask.taskTitle
        holder.descriptionTextView.text = currentTask.taskDescription
        holder.dateTextView.text = currentTask.taskDate

        // Set OnClickListener for the priority image
        holder.priorityImageView.setOnClickListener {
            val task = tasks[position]
            val newPriority = !task.taskPriority // Toggle priority
            priorityClickListener.onPriorityClick(task.id, newPriority)
        }


        if (currentTask.taskPriority) {
            holder.priorityImageView.setImageResource(R.drawable.liked_star)
        } else {
            holder.priorityImageView.setImageResource(R.drawable.unliked_star)
        }
    }

    override fun getItemCount() = tasks.size
}
