package com.example.androidlab_22_23

import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.androidlab_22_23.databinding.ItemCharacterBinding

class CharacterHolder(
    private val binding: ItemCharacterBinding,
//    private val glide: RequestManager,
//    private val action: (Character) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

//    private var character: Character? = null

//    init {
//        // use only one [setOnClickListener]
//        itemView.setOnClickListener {
//            character?.also(action)
//        }
//    }

    fun onBind(character: Character) {
        with(binding) {
            tvTitle.text = character.name
            tvDesc.text = character.species

//            tvTitle.setTextColor(
//                itemView.context.getColor(character.titleColor)
//            )

//            tvTitle.typeface = ResourcesCompat.getFont(itemView.context, book.font)
            /* bad way
            if (book.name == "Naruto") {
                tvTitle.setTextColor(
                    itemView.context.getColor(R.color.purple_700)
                )
            } else {
                tvTitle.setTextColor(
                    itemView.context.getColor(R.color.black)
                )
            }
            */

//            glide
//                .load(character.cover)
//                .apply(option)
//                .placeholder(R.drawable.cote_error_2)
//                .error(R.drawable.cote)
//                .into(ivCover)
        }
    }

//    fun updateCheck(isState: Boolean) {
//
//    }
}
