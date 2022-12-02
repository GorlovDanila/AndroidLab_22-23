package com.example.androidlab_22_23.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlab_22_23.R
import com.example.androidlab_22_23.model.Song

class MyListAdapter() : ListAdapter<Song, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(
            oldItem: Song,
            newItem: Song
        ): Boolean = (oldItem as? Song)?.id == (newItem as? Song)?.id

        override fun areContentsTheSame(
            oldItem: Song,
            newItem: Song
        ): Boolean = oldItem == newItem
    }
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.item_song -> SongHolder.create(parent)
//            R.layout.item_character -> CharacterHolder.create(parent, glide, deleteItem)
            else -> throw IllegalArgumentException()
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val currentItem = currentList[position]
        when (holder) {
            is SongHolder -> holder.onBind(currentItem as Song)
//            is CharacterHolder -> holder.onBind(currentItem as MyItem.Character)
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (currentList[position]) {
            is Song -> R.layout.item_song
//            is MyItem.Advertisement -> R.layout.item_advertisement
                else -> throw IllegalArgumentException()
        }
}