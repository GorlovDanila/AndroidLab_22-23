package com.example.androidlab_22_23.model

import com.example.androidlab_22_23.R

object SongRepository {
    val songsList: List<Song> = arrayListOf(
        Song(0, "Биография", R.drawable.biography, R.raw.biography, "Слава КПСС", "Ангельское True", false),
        Song(1, "Нагреваю", R.drawable.nagrevau, R.raw.nagrevau, "Heroinwater", "4 DAYS AFTER HB", false),
        Song(2, "Слеза Бандита", R.drawable.cry, R.raw.cry, "GONE.Fludd", "DIGITAL FANTAZY", false),
        Song(3, "Чему здесь праздновать?", R.drawable.present, R.raw.present  ,"92FLOWERS", "вышелпокурить", false),
        Song(4, "Город", R.drawable.brother, R.raw.city, "GONE.Fludd", "Поколение Брат", false),
        Song(5, "ШОССЕ", R.drawable.road, R.raw.road, "f0lk", "EVERYTHING DIES", false)
    )
}