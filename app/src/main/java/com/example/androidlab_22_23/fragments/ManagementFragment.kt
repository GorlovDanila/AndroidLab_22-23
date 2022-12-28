package com.example.androidlab_22_23.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidlab_22_23.R
import com.example.androidlab_22_23.database.Converters
import com.example.androidlab_22_23.databinding.FragmentManagementBinding
import com.example.androidlab_22_23.model.Note
import com.example.androidlab_22_23.model.NoteRepository
import com.example.androidlab_22_23.util.LocListenerInterface
import com.example.androidlab_22_23.util.MyLocationListener
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.util.*

class ManagementFragment : Fragment(R.layout.fragment_management), LocListenerInterface {
    private var binding: FragmentManagementBinding? = null
    private var calendar: Calendar? = null
    private var repository: NoteRepository? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationManager: LocationManager? = null
    private var location: Location? = null
    private var myLocationListener: MyLocationListener? = null
    private var latitude: Double? = null
    private var longitude: Double? = null

    private val permission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                latitude = 1.0
                longitude = 1.0
                Toast.makeText(requireContext(), "No GPS permissions", Toast.LENGTH_LONG)
            } else {
                checkPermissions()
            }
        }
//
//    private val settings =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}


//    private fun isLocationPermissionGranted(): Boolean {
//        return if (ActivityCompat.checkSelfPermission(
//                this,
//                android.Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this,
//                android.Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(
//                    android.Manifest.permission.ACCESS_FINE_LOCATION,
//                    android.Manifest.permission.ACCESS_COARSE_LOCATION
//                ),
//                requestcode
//            )
//            false
//        } else {
//            true
//        }
//    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        init()
        val idFromBundle = arguments?.getInt(ARG_ID_VALUE)
        Log.e("ID", idFromBundle.toString())
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        //getLastKnownLocation()
        Log.e("LATITUDE", latitude.toString())
        Log.e("LONGTITUDE", longitude.toString())
        binding = FragmentManagementBinding.bind(view)

        repository = NoteRepository(this@ManagementFragment.requireContext())

        calendar = Calendar.getInstance()
        binding?.run {
            if (idFromBundle == -1) {
                tvDate.text = calendar?.time.toString()
                if(latitude == 1.0 && longitude == 1.0) {
                    tvLatitude.text = "disabled"
                    tvLongitude.text = "disabled"
                } else {
                    tvLatitude.text = latitude.toString()
                    tvLongitude.text = longitude.toString()
                }
            } else if (idFromBundle != null) {
                lifecycleScope.launch {
                    val note: Note? = repository?.getNoteById(idFromBundle)
                    if (note != null) {
                        etTitle.setText(note.title, TextView.BufferType.EDITABLE)
                        etDescription.setText(note.description, TextView.BufferType.EDITABLE)
                        tvDate.text = Converters().fromTimestamp(note.date).toString()
                        if(note.latitude == 1.0 && note.longitude == 1.0) {
                            tvLatitude.text = "disabled"
                            tvLongitude.text = "disabled"
                        } else {
                            tvLatitude.text = note.latitude.toString()
                            tvLongitude.text = note.longitude.toString()
                        }
                    }
                }
            }

            btnSave.setOnClickListener {
                if (etTitle.text.isNotBlank() || etDescription.text.isNotBlank()) {
                    lifecycleScope.launch {

                        if(idFromBundle == -1 || idFromBundle != null && repository?.getNoteById(idFromBundle) == null) {
                            val note = Note(
                                null,
                                etTitle.text.toString(),
                                etDescription.text.toString(),
                                Converters().dateToTimestamp(calendar?.time),
                                longitude,
                                latitude
                            )
                            repository?.saveNote(note)
                        } else if(idFromBundle != null && repository?.getNoteById(idFromBundle) != null) {
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
                    .replace(R.id.fragment_container, MainFragment.newInstance(true))
                    .addToBackStack("ToManagementFragment").commit()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
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

    private fun init() {
         locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager?
         myLocationListener = MyLocationListener()
         myLocationListener?.setLocListenerInterface(this)
         checkPermissions()
    }

    private fun checkPermissions() {
        if(ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&  ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permission.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            permission.launch(Manifest.permission.ACCESS_FINE_LOCATION)

        } else {
           if(latitude != 1.0 && longitude != 1.0) {
               locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2L, 1F, myLocationListener!!)
           }
        }
    }

    fun getLastKnownLocation() {
//        if (ActivityCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            permission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//            permission.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
//        }
//        fusedLocationClient?.lastLocation
//            ?.addOnSuccessListener { location ->
//                if (location != null) {
//                    longitude = location.longitude
//                    latitude = location.latitude
//                }
//            }
    }

    companion object {
        private const val ARG_ID_VALUE = "arg_id_value"

        fun newInstance(id: Int) = ManagementFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_ID_VALUE, id)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onLocationChanged(loc: Location) {
        if(latitude != 1.0 && longitude != 1.0) {
            latitude = loc.latitude
            longitude = loc.longitude
        }
    }
}