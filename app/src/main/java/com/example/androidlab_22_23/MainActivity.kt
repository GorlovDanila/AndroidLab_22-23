package com.example.androidlab_22_23

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
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
            if (intent.getLongExtra(ARG_ID_VALUE, -1L) == -1L) {
                supportFragmentManager.beginTransaction().setCustomAnimations(
                    com.google.android.material.R.anim.abc_fade_in,
                    com.google.android.material.R.anim.abc_fade_out,
                    com.google.android.material.R.anim.abc_fade_in,
                    com.google.android.material.R.anim.abc_fade_out
                )
                    .add(R.id.fragment_container, MainFragment()).commit()
            } else {
                val id = intent.getLongExtra(ARG_ID_VALUE, -1)
                val prevId = intent.getLongExtra(ARG_PREV_ID_VALUE, -1)
                val nextId = intent.getLongExtra(ARG_NEXT_ID_VALUE, -1)
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

    companion object {
        private const val ARG_ID_VALUE = "arg_id_value"
        private const val ARG_PREV_ID_VALUE = "arg_prev_id_value"
        private const val ARG_NEXT_ID_VALUE = "arg_next_id_value"
    }
}