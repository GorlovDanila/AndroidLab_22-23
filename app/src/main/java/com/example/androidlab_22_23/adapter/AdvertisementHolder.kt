package com.example.androidlab_22_23.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.androidlab_22_23.R
import com.example.androidlab_22_23.databinding.ItemAdvertisementBinding
import com.example.androidlab_22_23.model.MyItem

class AdvertisementHolder(
    private val binding: ItemAdvertisementBinding,
    private val glide: RequestManager,
) : RecyclerView.ViewHolder(binding.root) {

    private var advertisement: MyItem.Advertisement? = null
    private val option = RequestOptions
        .diskCacheStrategyOf(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)

    fun onBind(advertisement: MyItem.Advertisement) {
        this.advertisement = advertisement
        with(binding) {
            tvAdvertisement.text = advertisement.title
            glide
                .load(advertisement.img)
                .apply(option)
                .fitCenter()
                .placeholder(R.drawable.load)
                .error(R.drawable.error)
                .into(ivAdvertisement)
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            glide: RequestManager,
        ): AdvertisementHolder = AdvertisementHolder(
            binding = ItemAdvertisementBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            glide = glide,
        )
    }
}
