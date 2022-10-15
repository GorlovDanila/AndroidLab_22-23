package com.example.androidlab_22_23

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlab_22_23.databinding.ItemCharacterBinding

class CharacterAdapter(
    private val list: List<Character>,
//    private val glide: RequestManager,
//    private val action: (Character) -> Unit,
) : RecyclerView.Adapter<CharacterHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterHolder = CharacterHolder(
        ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
//        glide = glide,
//        action = action
    )

    override fun onBindViewHolder(
        holder: CharacterHolder,
        position: Int
    ) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}
