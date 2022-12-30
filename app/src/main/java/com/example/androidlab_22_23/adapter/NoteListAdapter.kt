package com.example.androidlab_22_23.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlab_22_23.R
import com.example.androidlab_22_23.model.Note
import java.lang.IllegalArgumentException

class NoteListAdapter(
    private val actionNext: (Note) -> Unit,
    private val actionDelete: (Note) -> Unit,
) : ListAdapter<Note, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(
            oldItem: Note,
            newItem: Note
        ): Boolean = (oldItem as? Note)?.id == (newItem as? Note)?.id

        override fun areContentsTheSame(
            oldItem: Note,
            newItem: Note
        ): Boolean = oldItem == newItem
    }
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.item_note -> NoteHolder.create(parent, actionNext, actionDelete)
            else -> throw IllegalArgumentException()
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val currentItem = currentList[position]
        when (holder) {
            is NoteHolder -> holder.onBind(currentItem as Note)
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (currentList[position]) {
            is Note -> R.layout.item_note
            else -> throw IllegalArgumentException()
        }
}