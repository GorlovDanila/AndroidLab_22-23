package com.example.androidlab_22_23.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidlab_22_23.R
import com.example.androidlab_22_23.adapter.MyListAdapter
import com.example.androidlab_22_23.databinding.FragmentMainBinding
import com.example.androidlab_22_23.model.Song
import com.example.androidlab_22_23.model.SongRepository

class MainFragment : Fragment(R.layout.fragment_main) {

    private var binding: FragmentMainBinding? = null
    private var listAdapter: MyListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)


        binding?.run {
            Log.e("MAINContext", context.toString())

            listAdapter =
                MyListAdapter() {
                    onBookClick(it)
                }

            listAdapter?.submitList(SongRepository.songsList)
            rvSong.adapter = listAdapter
            rvSong.layoutManager = LinearLayoutManager(requireContext())
        }
    }


    private fun onBookClick(song: Song) {
        val prevId: Long = song.id - 1
        val nextId: Long = song.id + 1

        parentFragmentManager.beginTransaction().setCustomAnimations(
            com.google.android.material.R.anim.abc_fade_in,
            com.google.android.material.R.anim.abc_fade_out,
            com.google.android.material.R.anim.abc_fade_in,
            com.google.android.material.R.anim.abc_fade_out
        )
            .replace(R.id.fragment_container, DetailFragment.newInstance(prevId, song.id, nextId))
            .addToBackStack("ToMainFragment").commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}