package com.example.androidlab_22_23.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.androidlab_22_23.databinding.ItemSongBinding
import com.example.androidlab_22_23.model.Song
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class SongHolder(
    private val binding: ItemSongBinding,
) : RecyclerView.ViewHolder(binding.root) {
    private var song: Song? = null

    private val option = RequestOptions
        .diskCacheStrategyOf(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)

    //    init {
//        itemView.setOnClickListener {
//            song?.also(action)
//        }
//    }
    fun onBind(song: Song) {
        this.song = song
        with(binding) {
            ivCover.setImageURI(song.cover.toString().toUri())
            tvTitle.text = song.title
            tvAuthor.text = song.author
            tvAlbum.text = song.album

//            glide
//                .load(character.cover)
//                .apply(option)
//                .fitCenter()
//                .placeholder(R.drawable.load)
//                .error(R.drawable.error)
//                .into(ivCover)
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
//            glide: RequestManager,
//            deleteItem: ((Int)) -> Unit,
        ): SongHolder = SongHolder(
            binding = ItemSongBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
//            glide = glide,
//            deleteItem = deleteItem,
        )
    }
}