package com.example.androidlab_22_23

import android.app.AlertDialog
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.androidlab_22_23.databinding.FragmentMainBinding

class HelloFragment : Fragment(R.layout.fragment_main) {
    private var counter: Int = 0
    private var binding: FragmentMainBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt("s")
        }
        binding?.run {
            tvCounterValue.text = "Counter value: $counter"
            btnNextFragment.setOnClickListener {
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    parentFragmentManager.beginTransaction().setCustomAnimations(
                        com.google.android.material.R.anim.abc_fade_in,
                        com.google.android.material.R.anim.abc_fade_out,
                        com.google.android.material.R.anim.abc_fade_in,
                        com.google.android.material.R.anim.abc_fade_out
                    ).replace(R.id.fragment_container, SecondFragment.newInstance(counter))
                        .addToBackStack("ToMainFragment").commit()
                } else {
                    parentFragmentManager.beginTransaction().setCustomAnimations(
                        com.google.android.material.R.anim.abc_fade_in,
                        com.google.android.material.R.anim.abc_fade_out,
                        com.google.android.material.R.anim.abc_fade_in,
                        com.google.android.material.R.anim.abc_fade_out
                    ).replace(R.id.container_second, SecondFragment.newInstance(counter))
                        .commit()
                }
            }
            btnCounterPlusOne.setOnClickListener {
                counter++;
                tvCounterValue.text = "Counter value: $counter"

            }
            btnDialog.setOnClickListener {
                counter = onCreateDialog(counter)
                tvCounterValue.text = "Counter value: $counter"
            }
        }
    }

    private fun onCreateDialog(counter: Int): Int {
        var counterDialog = counter
        val view =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_fragment, null, false)
        val editText = view.findViewById<EditText>(R.id.et_text)
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
            val flag = checkET(editText)

            if (flag && editText.text.toString().toInt() in 0..100) {
                this.counter += editText.text.toString().toInt()
                dialog.dismiss()
            } else {
                editText.error = "Не верный формат данных"
            }
        }

        val neutralButton: Button = dialog.getButton(AlertDialog.BUTTON_NEUTRAL)
        neutralButton.setOnClickListener {
            val flag = checkET(editText)
            if (flag && editText.text.toString()
                    .toInt() in 0..100 && this.counter - editText.text.toString()
                    .toInt() > 0 && !editText.text.equals("")
            ) {
                this.counter -= editText.text.toString().toInt()
                dialog.dismiss()
            } else {
                editText.error = "Не верный формат данных"
            }
        }
        return counterDialog
    }

    private fun checkET(editText: EditText): Boolean {
        var flag = true
        val numberList = listOf(
            'a',
            'e',
            'i',
            'o',
            'u',
            'y',
            'b',
            'c',
            'd',
            'f',
            'g',
            'h',
            'j',
            'k',
            'l',
            'm',
            'n',
            'p',
            'q',
            'r',
            's',
            't',
            'v',
            'w',
            'x',
            'y',
            'z'
        )
        val text = editText.text.toString()
        if (!text.contains(' ')) {
            for (element1 in text) {
                for (element2 in numberList) {
                    if (element1.toString() == element2.toString()) {
                        flag = false
                        break
                    }
                    if (!flag)
                        break
                }
            }
        } else {
            if (text.contains(' '))
                flag = false
        }
        return flag
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    @Override
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("s", counter)
    }
}
