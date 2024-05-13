package com.example.taskpivot.adapter

import Task
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskpivot.R

class TaskAdapter(private var tasks: List<Task>, private val listener: OnDeleteClickListener) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    // Define an interface for click listener
    interface OnDeleteClickListener {
        fun onDeleteClick(taskId: Int)
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

        init {
            deleteImageView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (v == deleteImageView) {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val task = tasks[position]
                    listener.onDeleteClick(task.id) // Pass the task ID to the listener
                }
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
    }

    override fun getItemCount() = tasks.size
}
