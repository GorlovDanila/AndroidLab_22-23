package com.example.androidlab_22_23.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.androidlab_22_23.R
import com.example.androidlab_22_23.database.Converters
import com.example.androidlab_22_23.databinding.FragmentManagementBinding
import com.example.androidlab_22_23.model.Note
import com.example.androidlab_22_23.model.NoteRepository
import com.google.android.gms.location.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ManagementFragment : Fragment(R.layout.fragment_management) {
    private var binding: FragmentManagementBinding? = null
    private var calendar: Calendar? = null
    private var repository: NoteRepository? = null
    private var latitude: Double? = null
    private var longitude: Double? = null
    private var idFromBundle: Int? = null
    private var layout: String? = null
    private var date: Date? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        idFromBundle = arguments?.getInt(ARG_ID_VALUE)
        layout = arguments?.getString(ARG_LAYOUT_TYPE)
        binding = FragmentManagementBinding.bind(view)
        repository = NoteRepository(this@ManagementFragment.requireContext())
        calendar = Calendar.getInstance()
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        binding?.run {
            if (!checkPermission()) {
                requestPermission()
            }
            if (!checkPermission() || !isLocationEnabled()) {
                latitude = 1.0
                longitude = 1.0
                fragmentLogic()
            } else {
                getLastLocation()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun fragmentLogic() {
        date = calendar?.time
        binding?.run {
            if (idFromBundle == -1) {
//                tvDate.text = calendar?.time.toString()
                tvDate.text = getString(R.string.Date) + " " + SimpleDateFormat(
                    "dd.MM.yyyy HH:mm",
                    Locale.getDefault()
                ).format(date!!)
                if (latitude == 1.0 && longitude == 1.0) {
                    tvLatitude.text =
                        getString(R.string.latitudes) + " " + getString(R.string.disabled)
                    tvLongitude.text =
                        getString(R.string.Longtitude) + " " + getString(R.string.disabled)
                } else {
                    tvLatitude.text = getString(R.string.latitudes) + " " + latitude.toString()
                    tvLongitude.text = getString(R.string.Longtitude) + " " + longitude.toString()
                }
            } else if (idFromBundle != null) {
                lifecycleScope.launch {
                    val note: Note? = repository?.getNoteById(idFromBundle!!)
                    if (note != null) {
                        etTitle.setText(note.title, TextView.BufferType.EDITABLE)
                        etDescription.setText(note.description, TextView.BufferType.EDITABLE)
                        tvDate.text = getString(R.string.Date) + " " + SimpleDateFormat(
                            "dd.MM.yyyy HH:mm",
                            Locale.getDefault()
                        ).format(date!!)
                        if (latitude == 1.0 && longitude == 1.0) {
                            tvLatitude.text =
                                getString(R.string.latitudes) + " " + getString(R.string.disabled)
                            tvLongitude.text =
                                getString(R.string.Longtitude) + " " + getString(R.string.disabled)
                        } else {
                            tvLatitude.text =
                                getString(R.string.latitudes) + " " + latitude.toString()
                            tvLongitude.text =
                                getString(R.string.Longtitude) + " " + longitude.toString()
                        }
                    }
                }
            }

            btnSave.setOnClickListener {
                if (etTitle.text.isNotBlank() || etDescription.text.isNotBlank()) {
                    lifecycleScope.launch {

                        if (idFromBundle == -1 || idFromBundle != null && repository?.getNoteById(
                                idFromBundle!!
                            ) == null
                        ) {
                            val note = Note(
                                null,
                                etTitle.text.toString(),
                                etDescription.text.toString(),
                                Converters().dateToTimestamp(calendar?.time),
                                longitude,
                                latitude
                            )
                            repository?.saveNote(note)
                        } else if (idFromBundle != null && repository?.getNoteById(idFromBundle!!) != null) {
                            val note = Note(
                                idFromBundle,
                                etTitle.text.toString(),
                                etDescription.text.toString(),
                                Converters().dateToTimestamp(calendar?.time),
                                longitude,
                                latitude
                            )
                            repository?.updateNote(note)
                        }
                    }
                }

                parentFragmentManager.beginTransaction().setCustomAnimations(
                    com.google.android.material.R.anim.abc_fade_in,
                    com.google.android.material.R.anim.abc_fade_out,
                    com.google.android.material.R.anim.abc_fade_in,
                    com.google.android.material.R.anim.abc_fade_out
                )
                    .replace(R.id.fragment_container, MainFragment.newInstance(true, layout!!))
                    .addToBackStack("ToManagementFragment").commit()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_delete_all -> {
                lifecycleScope.launch {
                    repository?.deleteAll()
                }
                true
            }
            R.id.action_switch_theme -> {
                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                else
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }

    companion object {
        private const val ARG_ID_VALUE = "arg_id_value"
        private const val ARG_LAYOUT_TYPE = "arg_layout_type"

        fun newInstance(id: Int, layout: String) = ManagementFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_ID_VALUE, id)
                putString(ARG_LAYOUT_TYPE, layout)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        newLocationData()
                    } else {
                        longitude = location.longitude
                        latitude = location.latitude
                        fragmentLogic()
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please Turn on Your device Location",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun newLocationData() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, locationCallback, Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation: Location = locationResult.lastLocation
            longitude = lastLocation.longitude
            latitude = lastLocation.latitude
            fragmentLogic()
        }
    }

    private fun checkPermission(): Boolean {
        if (
            ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            1010
        )
    }
}