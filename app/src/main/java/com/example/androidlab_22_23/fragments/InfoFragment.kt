package com.example.androidlab_22_23.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.androidlab_22_23.model.Character
import com.example.androidlab_22_23.model.CharacterRepository
import com.example.androidlab_22_23.R
import com.example.androidlab_22_23.databinding.FragmentInfoBinding


class InfoFragment : Fragment(R.layout.fragment_info) {
    private var binding: FragmentInfoBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idFromBundle: Int? = arguments?.getInt(ARG_ID_VALUE)
        binding = FragmentInfoBinding.bind(view)
        val toolbar = view.findViewById(R.id.toolbar) as Toolbar
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
//        setSupportActionBar(toolbar)
        if ((activity as AppCompatActivity?)!!.supportActionBar != null) {
            (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }
        val character: Character = CharacterRepository.characters[idFromBundle!!]
        binding?.run {

            val uri: Uri = Uri.parse(character.cover)
            Log.e("uri", uri.toString())
            Glide.with(requireContext())
                .load(uri)
                .into(ivIcon)
            tvName.text = "Имя: ${character.name}\n"
            tvAffiliation.text = "Принадлежность: ${character.affiliation}\n"
            tvDesc.text = "Описание: ${character.description}\n"
            tvHistory.text = "История: \n\n${character.history}"
        }
    }

    companion object {
        private const val ARG_ID_VALUE = "arg_id_value"

        fun newInstance(id: Int) = InfoFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_ID_VALUE, id)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
