package com.example.androidlab_22_23.model

sealed interface MyItem {

    data class Advertisement(
        val id: Int,
        val title: String,
        val img: String
    ) : MyItem

    data class Character(
        val id: Int,
        var title: String,
        val cover: String,
        val affiliation: String,
    ) : MyItem
}
