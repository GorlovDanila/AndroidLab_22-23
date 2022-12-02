package com.example.androidlab_22_23.model

import com.example.androidlab_22_23.R
import com.example.androidlab_22_23.adapter.SongHolder

object SongRepository {
    val songsList: List<Song> = arrayListOf(
        Song(1, "PROJECT X", R.drawable.ic_project_x, R.raw.test, "Heroinwater", "NO COMMERCIAL LYRICS"),
        Song(2, "В миноре", R.drawable.ic_v_minore, R.raw.test, "Heroinwater", "NO COMMERCIAL LYRICS")
    )
//    val songsUI: MutableList<SongHolder>
//    get() = songsList.map {
//
//    }
}