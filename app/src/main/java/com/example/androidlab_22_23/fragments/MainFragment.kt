package com.example.androidlab_22_23.fragments

import android.Manifest
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
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

    private val permission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
//                result.launch(null)
            } else {
                Toast.makeText(
                    this@MainFragment.requireContext(),
                    "Разрешите доступ к камере!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val saveFlag = arguments?.getBoolean(ARG_SAVE_FLAG)
        binding = FragmentMainBinding.bind(view)
        if (saveFlag == true) {
            lifecycleScope.launch {
                listAdapter?.submitList(repository?.getAll())
                binding?.rvNote?.adapter = listAdapter
                binding?.rvNote?.layoutManager = GridLayoutManager(requireContext(), 2)
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
                    .replace(R.id.fragment_container, ManagementFragment.newInstance(-1))
                    .addToBackStack("ToMainFragment").commit()
            }
        }

        repository = NoteRepository(this@MainFragment.requireContext())

        lifecycleScope.launch {
            if (repository?.getAll()?.size != 0) {
                listAdapter?.submitList(repository?.getAll())
                binding?.rvNote?.adapter = listAdapter
                binding?.rvNote?.layoutManager = GridLayoutManager(requireContext(), 2)
                binding?.tvStart?.visibility = View.INVISIBLE
            } else {
                binding?.tvStart?.visibility = View.VISIBLE
            }
        }
    }

    private fun onNoteClick(note: Note) {
        val id = note.id

        if (id != null) {
//           permission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//            permission.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            parentFragmentManager.beginTransaction().setCustomAnimations(
                com.google.android.material.R.anim.abc_fade_in,
                com.google.android.material.R.anim.abc_fade_out,
                com.google.android.material.R.anim.abc_fade_in,
                com.google.android.material.R.anim.abc_fade_out
            )
                .replace(R.id.fragment_container, ManagementFragment.newInstance(id))
                .addToBackStack("ToMainFragment").commit()
        }
    }

    private fun onNoteDelete(note: Note) {
        lifecycleScope.launch {
            repository?.delete(note)
            if (repository?.getAll()?.size != 0) {
                listAdapter?.submitList(repository?.getAll())
                binding?.rvNote?.adapter = listAdapter
                binding?.rvNote?.layoutManager = GridLayoutManager(requireContext(), 2)
                binding?.tvStart?.visibility = View.INVISIBLE
            } else {
                listAdapter?.submitList(repository?.getAll())
                binding?.rvNote?.adapter = listAdapter
                binding?.rvNote?.layoutManager = GridLayoutManager(requireContext(), 2)
                binding?.tvStart?.visibility = View.VISIBLE
            }
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
                    binding?.rvNote?.layoutManager = GridLayoutManager(requireContext(), 2)
                    binding?.tvStart?.visibility = View.VISIBLE
                }
                true
            }
            R.id.action_switch_theme -> {
                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                else
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
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

        fun newInstance(save: Boolean) = MainFragment().apply {
            arguments = Bundle().apply {
                putBoolean(ARG_SAVE_FLAG, save)
            }
        }
    }
}