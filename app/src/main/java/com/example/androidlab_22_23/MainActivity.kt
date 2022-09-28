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
                binding?.tvEmpty?.visibility == View.INVISIBLE
            ) {
                binding?.tvAll?.visibility = View.VISIBLE
                binding?.tvPersonal?.visibility = View.VISIBLE
                binding?.tvItis?.visibility = View.VISIBLE
                binding?.tvEmpty?.visibility = View.VISIBLE
                binding?.guidelineDirectories?.setGuidelinePercent(0.1F)
                binding?.guidelineFirstChat?.setGuidelinePercent(0.19F)
                binding?.guidelineSecondChat?.setGuidelinePercent(0.28F)
                binding?.guidelineThirdChat?.setGuidelinePercent(0.37F)
                binding?.guidelineFourthChat?.setGuidelinePercent(0.46F)
                binding?.guidelineFifthChat?.setGuidelinePercent(0.55F)
            } else {
                binding?.tvAll?.visibility = View.INVISIBLE
                binding?.tvPersonal?.visibility = View.INVISIBLE
                binding?.tvItis?.visibility = View.INVISIBLE
                binding?.tvEmpty?.visibility = View.INVISIBLE
                binding?.guidelineDirectories?.setGuidelinePercent(0.08F)
                binding?.guidelineFirstChat?.setGuidelinePercent(0.17F)
                binding?.guidelineSecondChat?.setGuidelinePercent(0.26F)
                binding?.guidelineThirdChat?.setGuidelinePercent(0.35F)
                binding?.guidelineFourthChat?.setGuidelinePercent(0.44F)
                binding?.guidelineFifthChat?.setGuidelinePercent(0.53F)
            }
        }
    }
}