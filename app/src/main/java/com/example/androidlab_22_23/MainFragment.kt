package com.example.androidlab_22_23

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.androidlab_22_23.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {
    private var binding: FragmentMainBinding? = null

    private var adapter: CharacterAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        binding?.run {
            rvCharacter.adapter = CharacterAdapter(arrayListOf())
        }
    }
}