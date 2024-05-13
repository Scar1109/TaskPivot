package com.example.taskpivot.popups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.taskpivot.R

class DeleteConformationModel : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "DeleteConformationModel"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.delete_conformation_model, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cancelButton : Button = view.findViewById(R.id.cancel_delete_btn)

        cancelButton.setOnClickListener {
            dismiss()
        }

        val remove_btn : Button = view.findViewById(R.id.remove_btn)

        remove_btn.setOnClickListener {
            //delete action
        }
    }

}