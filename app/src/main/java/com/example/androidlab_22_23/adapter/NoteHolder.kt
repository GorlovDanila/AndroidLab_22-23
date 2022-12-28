package com.example.androidlab_22_23.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlab_22_23.databinding.ItemNoteBinding
import com.example.androidlab_22_23.model.Note

class NoteHolder(
    private val binding: ItemNoteBinding,
    private val actionNext: (Note) -> Unit,
    private val actionDelete: (Note) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private var note: Note? = null

    init {
        itemView.setOnClickListener {
            note?.also(actionNext)
        }
        binding.ivDelete.setOnClickListener {
            note?.also(actionDelete)
        }
    }

    fun onBind(note: Note) {
        this.note = note
        with(binding) {
            tvTitle.text = note.title
            tvDescription.text = note.description
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            actionNext: (Note) -> Unit,
            actionDelete: (Note) -> Unit,
        ): NoteHolder = NoteHolder(
            binding = ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            actionNext = actionNext,
            actionDelete = actionDelete,
        )
    }
}

