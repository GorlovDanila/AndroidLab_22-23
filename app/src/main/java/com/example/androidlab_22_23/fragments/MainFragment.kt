package com.example.androidlab_22_23.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidlab_22_23.R
import com.example.androidlab_22_23.adapter.NoteListAdapter
import com.example.androidlab_22_23.databinding.FragmentMainBinding
import com.example.androidlab_22_23.model.Note
import com.example.androidlab_22_23.model.NoteRepository
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {

    private var binding: FragmentMainBinding? = null
    private var repository: NoteRepository? = null
    private var listAdapter: NoteListAdapter? = null
    private var sharedPreferences: SharedPreferences? = null
    private var currentLayoutManager: String = "GridLayoutManager"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        sharedPreferences = requireContext().getSharedPreferences("myLayout", Context.MODE_PRIVATE)
        if (sharedPreferences != null) {
            currentLayoutManager = sharedPreferences?.getString("myLayoutType", "").toString()
        }
        val saveFlag = arguments?.getBoolean(ARG_SAVE_FLAG)
        binding = FragmentMainBinding.bind(view)
        if (saveFlag == true) {
            currentLayoutManager = arguments?.getString(ARG_LAYOUT_TYPE).toString()
            lifecycleScope.launch {
                listAdapter?.submitList(repository?.getAll())
                binding?.rvNote?.adapter = listAdapter
                if (currentLayoutManager == "GridLayoutManager") {
                    binding?.rvNote?.layoutManager = GridLayoutManager(requireContext(), 2)
                } else {
                    binding?.rvNote?.layoutManager = LinearLayoutManager(requireContext())
                }
                sharedPreferences?.edit()?.putString("myLayoutType", currentLayoutManager)?.apply()
            }
        }

        binding?.run {
            listAdapter = NoteListAdapter(actionNext = ::onNoteClick, actionDelete = ::onNoteDelete)

            fabCreate.setOnClickListener {
                parentFragmentManager.beginTransaction().setCustomAnimations(
                    com.google.android.material.R.anim.abc_fade_in,
                    com.google.android.material.R.anim.abc_fade_out,
                    com.google.android.material.R.anim.abc_fade_in,
                    com.google.android.material.R.anim.abc_fade_out
                )
                    .replace(
                        R.id.fragment_container,
                        ManagementFragment.newInstance(-1, currentLayoutManager)
                    )
                    .addToBackStack("ToMainFragment").commit()
            }
        }

        repository = NoteRepository(this@MainFragment.requireContext())

        lifecycleScope.launch {
            if (repository?.getAll()?.size != 0) {
                listAdapter?.submitList(repository?.getAll())
                binding?.rvNote?.adapter = listAdapter
                if (currentLayoutManager == "GridLayoutManager") {
                    binding?.rvNote?.layoutManager = GridLayoutManager(requireContext(), 2)
                } else {
                    binding?.rvNote?.layoutManager = LinearLayoutManager(requireContext())
                }
                binding?.tvStart?.visibility = View.INVISIBLE
            } else {
                binding?.tvStart?.visibility = View.VISIBLE
            }
            sharedPreferences?.edit()?.putString("myLayoutType", currentLayoutManager)?.apply()
        }
    }

    private fun onNoteClick(note: Note) {
        val id = note.id

        if (id != null) {
            parentFragmentManager.beginTransaction().setCustomAnimations(
                com.google.android.material.R.anim.abc_fade_in,
                com.google.android.material.R.anim.abc_fade_out,
                com.google.android.material.R.anim.abc_fade_in,
                com.google.android.material.R.anim.abc_fade_out
            )
                .replace(
                    R.id.fragment_container,
                    ManagementFragment.newInstance(id, currentLayoutManager)
                )
                .addToBackStack("ToMainFragment").commit()
        }
    }

    private fun onNoteDelete(note: Note) {
        lifecycleScope.launch {
            repository?.delete(note)
            if (repository?.getAll()?.size != 0) {
                listAdapter?.submitList(repository?.getAll())
                binding?.rvNote?.adapter = listAdapter
                if (currentLayoutManager == "GridLayoutManager") {
                    binding?.rvNote?.layoutManager = GridLayoutManager(requireContext(), 2)
                } else {
                    binding?.rvNote?.layoutManager = LinearLayoutManager(requireContext())
                }
                binding?.tvStart?.visibility = View.INVISIBLE
            } else {
                listAdapter?.submitList(repository?.getAll())
                binding?.rvNote?.adapter = listAdapter
                if (currentLayoutManager == "GridLayoutManager") {
                    binding?.rvNote?.layoutManager = GridLayoutManager(requireContext(), 2)
                } else {
                    binding?.rvNote?.layoutManager = LinearLayoutManager(requireContext())
                }
                binding?.tvStart?.visibility = View.VISIBLE
            }
            sharedPreferences?.edit()?.putString("myLayoutType", currentLayoutManager)?.apply()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_delete_all -> {
                lifecycleScope.launch {
                    repository?.deleteAll()
                    listAdapter?.submitList(repository?.getAll())
                    binding?.rvNote?.adapter = listAdapter
                    if (currentLayoutManager == "GridLayoutManager") {
                        binding?.rvNote?.layoutManager = GridLayoutManager(requireContext(), 2)
                    } else {
                        binding?.rvNote?.layoutManager = LinearLayoutManager(requireContext())
                    }
                    sharedPreferences?.edit()?.putString("myLayoutType", currentLayoutManager)
                        ?.apply()
                    binding?.tvStart?.visibility = View.VISIBLE
                }
                true
            }
            R.id.action_switch_theme -> {
                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                true
            }
            R.id.action_switch_layout -> {
                if (binding?.rvNote?.layoutManager.toString()
                        .replace("androidx.recyclerview.widget.", "")
                        .startsWith("GridLayoutManager", 0)
                ) {
                    lifecycleScope.launch {
                        listAdapter?.submitList(repository?.getAll())
                        binding?.rvNote?.adapter = listAdapter
                        binding?.rvNote?.layoutManager = LinearLayoutManager(requireContext())
                        currentLayoutManager = "LinearLayoutManager"
                        sharedPreferences?.edit()?.putString("myLayoutType", currentLayoutManager)
                            ?.apply()
                    }
                } else {
                    lifecycleScope.launch {
                        listAdapter?.submitList(repository?.getAll())
                        binding?.rvNote?.adapter = listAdapter
                        binding?.rvNote?.layoutManager = GridLayoutManager(requireContext(), 2)
                        currentLayoutManager = "GridLayoutManager"
                        sharedPreferences?.edit()?.putString("myLayoutType", currentLayoutManager)
                            ?.apply()
                    }
                }
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        private const val ARG_SAVE_FLAG = "arg_save_flag"
        private const val ARG_LAYOUT_TYPE = "arg_layout_type"

        fun newInstance(save: Boolean, layout: String) = MainFragment().apply {
            arguments = Bundle().apply {
                putBoolean(ARG_SAVE_FLAG, save)
                putString(ARG_LAYOUT_TYPE, layout)
            }
        }
    }
}