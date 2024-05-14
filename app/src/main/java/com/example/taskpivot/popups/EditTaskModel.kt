package com.example.taskpivot.popups

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.taskpivot.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


class EditTaskModel : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "EditSummeryModelSheet"
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

        val dateTxt : TextView = view.findViewById(R.id.date_txt)
        val buttonDate : RelativeLayout = view.findViewById(R.id.button_date)

        dateTxt.text = "YYYY-MM-DD"

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