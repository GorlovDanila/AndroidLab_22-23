package com.example.androidlab_22_23.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidlab_22_23.adapter.CharacterAdapter
import com.example.androidlab_22_23.adapter.CharacterHolder
import com.example.androidlab_22_23.model.CharacterRepository
import com.example.androidlab_22_23.R
import com.example.androidlab_22_23.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {
    private var binding: FragmentMainBinding? = null
    private var adapter: CharacterAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
//        (activity as AppCompatActivity?)!!.setSupportActionBar()
//        setSupportActionBar(toolbar)
//        if ((activity as AppCompatActivity?)!!.supportActionBar != null) {
//            (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        }
//        val glide = Glide.with(this@MainFragment)
//        CharacterRepository.characters.forEach {
//         glide.load(it.cover)
//             .apply(R.drawable.load)
//             .preload()
//        }
        binding?.run {
            val itemDecoration: RecyclerView.ItemDecoration =
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            adapter = CharacterAdapter(
                list = CharacterRepository.characters,
                glide = Glide.with(this@MainFragment)
            ) {
                parentFragmentManager.beginTransaction().setCustomAnimations(
                    com.google.android.material.R.anim.abc_fade_in,
                    com.google.android.material.R.anim.abc_fade_out,
                    com.google.android.material.R.anim.abc_fade_in,
                    com.google.android.material.R.anim.abc_fade_out
                )
                    .replace(R.id.fragment_container, InfoFragment.newInstance(it.id))
                    .addToBackStack("ToMainFragment").commit()
            }
            rvCharacter.adapter = adapter
            rvCharacter.layoutManager = LinearLayoutManager(requireContext())
            rvCharacter.addItemDecoration(itemDecoration)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}