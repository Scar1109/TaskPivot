package com.example.taskpivot.popups

import TaskViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.taskpivot.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


class EditTaskModel : BottomSheetDialogFragment() {

    private lateinit var taskViewModel: TaskViewModel

    companion object {
        const val TAG = "EditionModel"
        const val ARG_TASK_ID = "task_id"

        fun newInstance(taskId: Int): EditTaskModel {
            val args = Bundle().apply {
                putInt(ARG_TASK_ID, taskId)
            }
            return EditTaskModel().apply {
                arguments = args
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.edit_task_model, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cancelButton : Button = view.findViewById(R.id.summery_edit_cancel_btn)

        cancelButton.setOnClickListener {
            dismiss()
        }

        val buttonDate : RelativeLayout = view.findViewById(R.id.button_date)

        taskViewModel = ViewModelProvider(requireActivity()).get(TaskViewModel::class.java)

        val taskId = arguments?.getInt(ARG_TASK_ID) ?: -1

        if (taskId != -1) {
            // Retrieve the task information from the ViewModel
            val task = taskViewModel.getTaskById(taskId)

            // Update the text fields with task details
            task?.let {
                val taskTitleLayout: TextInputLayout = view.findViewById(R.id.task_title)
                val taskTitleEditText: EditText? = taskTitleLayout.editText
                val taskDescriptionEditText: EditText = view.findViewById(R.id.task_description)
                val dateTxt: TextView = view.findViewById(R.id.date_txt)
                dateTxt.setTextColor(ContextCompat.getColor(requireContext(), R.color.input_hint_color))

                taskTitleEditText?.setText(it.taskTitle)
                taskDescriptionEditText.setText(it.taskDescription)
                dateTxt.text = it.taskDate
            }
        }





        buttonDate.setOnClickListener{
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

            val dateTxt : TextView = requireView().findViewById(R.id.date_txt)

            dateTxt.text = selectedDate
            dateTxt.setTextColor(ContextCompat.getColor(requireContext(), R.color.input_hint_color))

        }


        datePicker.show(childFragmentManager, "datePicker")
    }

}