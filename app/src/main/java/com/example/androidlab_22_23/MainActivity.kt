package com.example.androidlab_22_23

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlab_22_23.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    private val permission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                result.launch(null)
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Разрешите доступ к камере!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    private val result = registerForActivityResult(PickImage()) { result ->
        Log.e("BITMAP", result.toString())
        binding?.imgIntent?.setImageBitmap(result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        binding?.run {
            btnPinImg.setOnClickListener {
                permission.launch(Manifest.permission.CAMERA)
            }
        }
    }
}