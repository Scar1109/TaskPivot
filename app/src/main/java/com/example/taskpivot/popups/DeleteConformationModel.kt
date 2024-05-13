package com.example.taskpivot.popups

import TaskViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.taskpivot.R
import com.example.taskpivot.modules.HomeScreen

class DeleteConformationModel : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "DeleteConformationModel"
        const val ARG_TASK_ID = "task_id"

        fun newInstance(taskId: Int): DeleteConformationModel {
            val args = Bundle().apply {
                putInt(ARG_TASK_ID, taskId)
            }
            return DeleteConformationModel().apply {
                arguments = args
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.delete_conformation_model, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        val taskViewModel = ViewModelProvider(requireActivity()).get(TaskViewModel::class.java)


        val cancelButton : Button = view.findViewById(R.id.cancel_delete_btn)

        cancelButton.setOnClickListener {
            dismiss()
        }

        val remove_btn : Button = view.findViewById(R.id.remove_btn)

        remove_btn.setOnClickListener {
            val taskId = arguments?.getInt(ARG_TASK_ID) ?: -1
            val deletedRows = taskViewModel.deleteTask(taskId)
            if (deletedRows > 0) {
                Toast.makeText(requireContext(), "Task deleted successfully", Toast.LENGTH_SHORT).show()
                (requireActivity() as HomeScreen).deleteTaskAndRefresh(taskId)
            } else {
                Toast.makeText(requireContext(), "Failed to delete task", Toast.LENGTH_SHORT).show()
            }
            dismiss()
        }

    }

}