package com.example.androidlab_22_23

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlab_22_23.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private var binding : ActivitySecondBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
    }
}