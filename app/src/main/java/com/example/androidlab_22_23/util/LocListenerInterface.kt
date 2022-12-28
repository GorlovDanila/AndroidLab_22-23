package com.example.androidlab_22_23.util

import android.location.Location

interface LocListenerInterface {
    fun onLocationChanged(loc: Location)
}