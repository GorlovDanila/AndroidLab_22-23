package com.example.androidlab_22_23.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.androidlab_22_23.databinding.ItemSongBinding
import com.example.androidlab_22_23.model.Song
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.androidlab_22_23.R

class SongHolder(
    private val binding: ItemSongBinding,
    private val action: (Song) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private var song: Song? = null

    init {
        itemView.setOnClickListener {
            song?.also(action)
            //it.setBackgroundColor(Color.BLUE)
        }
    }

    fun onBind(song: Song) {
        this.song = song
        with(binding) {
            ivCover.setImageResource(song.cover)
            tvTitle.text = song.title
            tvAuthor.text = song.author
            if(song.isPlaying) {
                ivIcon.setImageResource(R.drawable.ic_baseline_music_note_24)
            }
//            tvAlbum.text = song.album

        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
//            glide: RequestManager,
//            deleteItem: ((Int)) -> Unit,
            action: (Song) -> Unit
        ): SongHolder = SongHolder(
            binding = ItemSongBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            action = action
//            glide = glide,
//            deleteItem = deleteItem,
        )
    }
}