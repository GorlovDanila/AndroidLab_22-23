package com.example.androidlab_22_23.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.androidlab_22_23.model.Character
import com.example.androidlab_22_23.R
import com.example.androidlab_22_23.databinding.ItemCharacterBinding

class CharacterAdapter(
    private val list: List<Character>,
    private val glide: RequestManager,
    private val action: (Character) -> Unit,
) : RecyclerView.Adapter<CharacterHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterHolder = CharacterHolder(
        binding = ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        glide = glide,
        action = action
    )

    override fun onBindViewHolder(
        holder: CharacterHolder,
        position: Int
    ) {
        holder.onBind(list[position])
        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.scale)
    }

    override fun getItemCount(): Int = list.size
}
