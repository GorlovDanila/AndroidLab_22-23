package com.example.androidlab_22_23

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlab_22_23.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)?.also {
            setContentView(it.root)
        }

        binding?.btnFloatingAction?.setOnClickListener {
            if (binding?.tvAll?.visibility == View.INVISIBLE &&
                binding?.tvPersonal?.visibility == View.INVISIBLE &&
                binding?.tvItis?.visibility == View.INVISIBLE &&
                binding?.tvEmpty?.visibility == View.INVISIBLE) {
                binding?.tvAll?.visibility = View.VISIBLE
                binding?.tvPersonal?.visibility = View.VISIBLE
                binding?.tvItis?.visibility = View.VISIBLE
                binding?.tvEmpty?.visibility = View.VISIBLE
            } else {
                binding?.tvAll?.visibility = View.INVISIBLE
                binding?.tvPersonal?.visibility = View.INVISIBLE
                binding?.tvItis?.visibility = View.INVISIBLE
                binding?.tvEmpty?.visibility = View.INVISIBLE
            }
        }
    }
}