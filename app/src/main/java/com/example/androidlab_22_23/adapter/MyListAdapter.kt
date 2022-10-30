package com.example.androidlab_22_23.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.androidlab_22_23.R
import com.example.androidlab_22_23.model.MyItem
import java.lang.IllegalArgumentException

class MyListAdapter(
    private val glide: RequestManager,
    private val deleteItem: ((Int) -> Unit),
) : ListAdapter<MyItem, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<MyItem>() {
        override fun areItemsTheSame(
            oldItem: MyItem,
            newItem: MyItem
        ): Boolean = (oldItem as? MyItem.Character)?.id == (newItem as? MyItem.Character)?.id ||
                (oldItem as? MyItem.Advertisement)?.id == (newItem as? MyItem.Advertisement)?.id

        override fun areContentsTheSame(
            oldItem: MyItem,
            newItem: MyItem
        ): Boolean = oldItem == newItem
    }
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder =
         when(viewType) {
             R.layout.item_advertisement -> AdvertisementHolder.create(parent, glide)
             R.layout.item_character -> CharacterHolder.create(parent, glide, deleteItem)
             else -> throw IllegalArgumentException()
         }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val currentItem = currentList[position]
        when(holder) {
            is AdvertisementHolder -> holder.onBind(currentItem as MyItem.Advertisement)
            is CharacterHolder -> holder.onBind(currentItem as MyItem.Character)
        }
    }

    override fun getItemViewType(position: Int): Int =
        when(currentList[position]) {
            is MyItem.Character -> R.layout.item_character
            is MyItem.Advertisement -> R.layout.item_advertisement
        }
}