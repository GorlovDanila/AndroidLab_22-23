package com.example.androidlab_22_23.util

import android.location.Location
import android.location.LocationListener

class MyLocationListener : LocationListener {
    private lateinit var locListenerInterface: LocListenerInterface
    override fun onLocationChanged(location: Location) {
        locListenerInterface.onLocationChanged(location)
    }

    fun setLocListenerInterface(locListenerInterface: LocListenerInterface) {
        this.locListenerInterface = locListenerInterface
    }
}