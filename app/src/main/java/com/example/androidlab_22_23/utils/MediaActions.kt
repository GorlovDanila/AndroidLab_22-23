package com.example.androidlab_22_23.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class MediaActions(val key: Int): Parcelable {
    PLAY(0), PAUSE(1), STOP(2), PREVIOUS(3), NEXT(4)
}