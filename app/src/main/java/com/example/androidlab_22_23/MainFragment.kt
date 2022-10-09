package com.example.androidlab_22_23

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidlab_22_23.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {
    private var counter: Int = 0
    private var binding: FragmentMainBinding? = null
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("count", counter)
    }

    //    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)
//    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
//        MainFragment.setRetainInstance(true)
        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt("count")
        }
        val counterFromDialog: Int? = arguments?.getInt(ARG_COUNTER_VALUE_DIALOG)
        Log.e("FFFFF", counterFromDialog.toString())
        if (counterFromDialog != null) {
            counter = counterFromDialog.toInt()
        }
        binding?.run {

            btnNextFragment.setOnClickListener {
                parentFragmentManager.beginTransaction().setCustomAnimations(
                    com.google.android.material.R.anim.abc_fade_in,
                    com.google.android.material.R.anim.abc_fade_out,
                    com.google.android.material.R.anim.abc_fade_in,
                    com.google.android.material.R.anim.abc_fade_out
                ).replace(R.id.fragment_container, SecondFragment.newInstance(counter))
                    .addToBackStack("ToMainFragment").commit()
            }
            btnCounterPlusOne.setOnClickListener {
                counter++;
                tvCounterValue.text = "Counter value: $counter"
            }
            btnDialog.setOnClickListener {
                MyDialog().show(parentFragmentManager, "TAG")

            }
        }
    }

    companion object {

        private const val ARG_COUNTER_VALUE_DIALOG = "arg_counter_value"

        fun newInstanceDialog(counterDialog: Int) = MainFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_COUNTER_VALUE_DIALOG, counterDialog)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}