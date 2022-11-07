package com.example.androidlab_22_23.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.androidlab_22_23.model.Character
import com.example.androidlab_22_23.R
import com.example.androidlab_22_23.databinding.ItemCharacterBinding

class CharacterHolder(
    private val binding: ItemCharacterBinding,
    private val glide: RequestManager,
    private val action: (Character) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private val option = RequestOptions
        .diskCacheStrategyOf(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)

    fun onBind(character: Character) {
        with(binding) {
            tvTitle.text = character.name
            tvDesc.text = character.affiliation

            glide
                .load(character.cover)
                .apply(option)
                .fitCenter()
                .placeholder(R.drawable.load)
                .error(R.drawable.error)
                .into(ivCover)

            root.setOnClickListener {
                action(character)
            }
        }
    }
}
