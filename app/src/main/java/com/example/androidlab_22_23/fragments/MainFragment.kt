package com.example.androidlab_22_23.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidlab_22_23.R
import com.example.androidlab_22_23.adapter.MyListAdapter
import com.example.androidlab_22_23.databinding.FragmentMainBinding
import com.example.androidlab_22_23.model.MyItem
import com.example.androidlab_22_23.model.MyRepository
import com.example.androidlab_22_23.model.MyRepository.addItem
import com.example.androidlab_22_23.model.MyRepository.deleteItem
import com.example.androidlab_22_23.utils.SwipeDelete

class MainFragment : Fragment(R.layout.fragment_main) {

    private var binding: FragmentMainBinding? = null
    private var listAdapter: MyListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        binding?.run {

            fab.setOnClickListener {
                val dialog = MyDialogFragment(addItem = ::addItem)
                dialog.show(parentFragmentManager, "MyDialog")
            }

            val itemDecoration: RecyclerView.ItemDecoration =
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)

            listAdapter =
                MyListAdapter(glide = Glide.with(this@MainFragment), deleteItem = ::deleteItem)
            MyRepository.generateList(20)
            rvCharacter.scrollToPosition(10)
            listAdapter?.submitList(MyRepository.itemList)
            rvCharacter.adapter = listAdapter
            SwipeDelete(binding!!.root, listAdapter).attachToRecyclerView(binding!!.rvCharacter)

            rvCharacter.adapter = listAdapter
            rvCharacter.layoutManager = LinearLayoutManager(requireContext())
            rvCharacter.addItemDecoration(itemDecoration)
        }
    }

    private fun deleteItem(position: Int) {
        MyRepository.deleteItem(position)
        listAdapter?.submitList(MyRepository.itemList)
    }

    private fun addItem(position: Int, item: MyItem.Character) {
        MyRepository.addItem(position, item)
        listAdapter?.submitList(MyRepository.itemList)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}