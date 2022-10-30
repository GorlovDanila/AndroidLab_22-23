package com.example.androidlab_22_23.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.androidlab_22_23.databinding.FragmentDialogBinding
import com.example.androidlab_22_23.model.MyItem
import com.example.androidlab_22_23.model.MyRepository

class MyDialogFragment(
    val addItem: (position: Int, MyItem.Character) -> Unit
) : DialogFragment() {
    private var _binding: FragmentDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnNegative.setOnClickListener {
                dismiss()
            }
            btnPositive.setOnClickListener {
                val title = etTitle.text.toString()
                val affiliation = etInfo.text.toString()
                val cover =
                    "https://avatars.mds.yandex.net/i?id=2b20ca6f19e2ffaf6f9a48b24a5d2713-5676860-images-thumbs&n=13"
                val position = if (etPosition.text.toString().isNotBlank()
                ) Integer.valueOf(etPosition.text.toString())
                else MyRepository.itemList.size
                addItem(
                    position, MyItem.Character(
                        id = title.length + affiliation.length + cover.length + position,
                        title = title,
                        affiliation = affiliation,
                        cover = cover,
                    )
                )
                dismiss()
            }
        }
    }
}