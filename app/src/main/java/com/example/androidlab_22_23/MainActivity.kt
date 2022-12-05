package com.example.androidlab_22_23

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidlab_22_23.databinding.ActivityMainBinding
import com.example.androidlab_22_23.fragments.DetailFragment
import com.example.androidlab_22_23.fragments.MainFragment

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        binding?.run {
            if (intent.getLongExtra("arg_id_value", -1L) == -1L) {
                supportFragmentManager.beginTransaction().setCustomAnimations(
                    com.google.android.material.R.anim.abc_fade_in,
                    com.google.android.material.R.anim.abc_fade_out,
                    com.google.android.material.R.anim.abc_fade_in,
                    com.google.android.material.R.anim.abc_fade_out
                )
                    .add(R.id.fragment_container, MainFragment()).commit()
            } else {
                val id = intent.getLongExtra("arg_id_value", -1)
                val prevId = intent.getLongExtra("arg_prev_id_value", -1)
                val nextId = intent.getLongExtra("arg_next_id_value", -1)
                supportFragmentManager.beginTransaction().setCustomAnimations(
                    com.google.android.material.R.anim.abc_fade_in,
                    com.google.android.material.R.anim.abc_fade_out,
                    com.google.android.material.R.anim.abc_fade_in,
                    com.google.android.material.R.anim.abc_fade_out
                )
                    .add(R.id.fragment_container, DetailFragment.newInstance(prevId, id, nextId))
                    .commit()
            }
        }
    }
}