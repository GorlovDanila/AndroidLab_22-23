package com.example.androidlab_22_23

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidlab_22_23.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        binding?.run {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MainFragment(), "main").commit()
        }
    }
}