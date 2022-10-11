package com.example.androidlab_22_23

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.androidlab_22_23.databinding.FragmentSecondBinding

class SecondFragment : Fragment(R.layout.fragment_second) {
    private var binding: FragmentSecondBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val counterFromBundle: Int? = arguments?.getInt(ARG_COUNTER_VALUE)
        if (counterFromBundle.toString().toInt() in 0..50) {
            val colorValue = ContextCompat.getColor(requireContext(), R.color.purple_200)
            view.setBackgroundColor(colorValue)

        }
        if (counterFromBundle.toString().toInt() in 51..100) {
            val colorValue = ContextCompat.getColor(requireContext(), R.color.purple_500)
            view.setBackgroundColor(colorValue)

        }
        if (counterFromBundle.toString().toInt() > 100) {
            val colorValue = ContextCompat.getColor(requireContext(), R.color.purple_700)
            view.setBackgroundColor(colorValue)

        }

        binding = FragmentSecondBinding.bind(view)

        binding?.run {
            binding!!.tvCounter.text = "Counter value: $counterFromBundle"
        }
    }

    companion object {

        private const val ARG_COUNTER_VALUE = "arg_counter_value"

        fun newInstance(counter: Int) = SecondFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_COUNTER_VALUE, counter)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}