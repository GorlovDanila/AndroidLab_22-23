package com.example.androidlab_22_23

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.androidlab_22_23.databinding.DialogFragmentBinding

class MyDialogFragment : DialogFragment(R.layout.dialog_fragment) {
    private var binding : DialogFragmentBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun onCreateDialog(counter: Int): Int {
        var counterDialog = counter
        val view =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_fragment, null, false)
        val editText = binding?.etText
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("DialogFragment")
            .setMessage("Increase counter by a given number?")
            .setView(view)
            .setPositiveButton("Accept", null)
            .setNeutralButton("Wait", null)
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()

        val positiveButton: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            val flag = HelloFragment().checkET(editText)

            if (flag && editText?.text.toString().toInt() in 0..100) {
                counterDialog += editText?.text.toString().toInt()
                dialog.dismiss()
            } else {
                editText?.error = "Не верный формат данных"
            }
        }

        val neutralButton: Button = dialog.getButton(AlertDialog.BUTTON_NEUTRAL)
        neutralButton.setOnClickListener {
            val flag = HelloFragment().checkET(editText)
            if (flag && editText?.text.toString()
                    .toInt() in 0..100 && counterDialog - editText?.text.toString()
                    .toInt() > 0
            ) {
                counterDialog -= editText?.text.toString().toInt()
                dialog.dismiss()
            } else {
                editText?.error = "Не верный формат данных"
            }
        }
        return counterDialog
    }
}