package com.example.androidlab_22_23.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.androidlab_22_23.R
import com.example.androidlab_22_23.databinding.ItemCharacterBinding
import com.example.androidlab_22_23.model.MyItem

class CharacterHolder(
    private val binding: ItemCharacterBinding,
    private val glide: RequestManager,
    private val deleteItem: ((Int)) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private var character: MyItem.Character? = null

    private val option = RequestOptions
        .diskCacheStrategyOf(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)

    init {
        binding.ivDel.setOnClickListener {
            deleteItem?.invoke(adapterPosition)
        }
    }

    fun onBind(character: MyItem.Character) {
        this.character = character
        with(binding) {
            tvTitle.text = character.title
            tvDesc.text = character.affiliation

            glide
                .load(character.cover)
                .apply(option)
                .fitCenter()
                .placeholder(R.drawable.load)
                .error(R.drawable.error)
                .into(ivCover)
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            glide: RequestManager,
            deleteItem: ((Int)) -> Unit,
        ): CharacterHolder = CharacterHolder(
            binding = ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            glide = glide,
            deleteItem = deleteItem,
        )
    }
}