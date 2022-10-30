package com.example.androidlab_22_23.utils

import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlab_22_23.adapter.MyListAdapter
import com.example.androidlab_22_23.model.MyRepository

class SwipeDelete(view: View, adapter: MyListAdapter?) :

    ItemTouchHelper(object : SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            MyRepository.deleteItem(viewHolder.adapterPosition)
            adapter?.submitList(MyRepository.itemList)
        }
    })