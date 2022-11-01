package com.example.androidlab_22_23

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {
//    private var binding: Act
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    val controller =
        (supportFragmentManager.findFragmentById(R.id.main_fragment) as NavHostFragment)
            .navController
    }
}