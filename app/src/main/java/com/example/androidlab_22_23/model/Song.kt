package com.example.androidlab_22_23.model

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes

data class Song(
    val id: Long,
    val title: String,
    @DrawableRes val cover: Int,
    @RawRes val audio: Int,
    val author: String,
    val album: String,
) {
}