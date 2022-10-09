package com.example.androidlab_22_23

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class MyDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val editText = EditText(requireContext())
        var counterDialog: Int = 0
        //val view = TextInputLayout.from(requireContext()).inflate(R.layout.fragment_home, null, false)
        return AlertDialog.Builder(requireContext())
            .setTitle("DialogFragment")
            .setMessage("Increase counter by a given number?")
            .setView(editText)
            .setPositiveButton("Accept") { dialog, _ ->
                try {
                    if (editText.text.toString().toInt() in 0..100 && !editText.text.equals("")) {
                        counterDialog += editText.text.toString().toInt()
                    }
                } catch (e : Exception) {
                    editText.error = "Не верный формат данных"
                }
                MainFragment.newInstanceDialog(counterDialog)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss()
            }
            .setNeutralButton("Wait") { dialog, _ ->
                try {
                if (editText.text.toString().toInt() in 0..100 && counterDialog - editText.text.toString().toInt() > 0 && !editText.text.equals(""))
                    counterDialog -= editText.text.toString().toInt()
                } catch (e : Exception) {
                    editText.error = "Не верный формат данных"
                }
                MainFragment.newInstanceDialog(counterDialog)
            }
            .create()
    }

    companion object {

//        fun newInstance(fragmentManager: FragmentManager) {
//            return MyDialog().show(fragmentManager, "Tag")
//        }
        private const val ARG_COUNTER_VALUE_DIALOG = "arg_counter_value"

        fun newInstance(counterDialog: Int) = MyDialog().apply {
            arguments = Bundle().apply {
                putInt(ARG_COUNTER_VALUE_DIALOG, counterDialog)
            }
        }
    }
}


