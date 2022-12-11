package com.example.androidlab_22_23

import android.content.Intent
import android.content.Intent.EXTRA_STREAM
import android.content.Intent.EXTRA_TEXT
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity


class ShareActivity : AppCompatActivity() {
    private var binding: com.example.androidlab_22_23.databinding.ActivityShareBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val action: String? = intent.action
        val actionType = intent.type

        binding =
            com.example.androidlab_22_23.databinding.ActivityShareBinding.inflate(layoutInflater)
                .also {
                    setContentView(it.root)
                }
        if (action == Intent.ACTION_SEND && actionType == "text/plain") {
            binding?.ivRes?.visibility = View.GONE
            binding?.vvRes?.visibility = View.GONE
            binding?.tvRes?.text = intent.getStringExtra(EXTRA_TEXT)
        }
        if (action == Intent.ACTION_SEND && actionType!!.startsWith("image/")) {
            binding?.tvRes?.visibility = View.GONE
            binding?.vvRes?.visibility = View.GONE
            val imageUri = intent.getParcelableExtra<Parcelable>(EXTRA_STREAM) as Uri?
            binding?.ivRes?.setImageURI(imageUri)
        }
        if(action == Intent.ACTION_SEND && actionType!!.startsWith("video/")) {
            binding?.tvRes?.visibility = View.GONE
            binding?.ivRes?.visibility = View.GONE
            val videoUri = intent.getParcelableExtra<Parcelable>(EXTRA_STREAM) as Uri?
            binding?.vvRes?.setVideoURI(videoUri)
            val mediaController = MediaController(this)
            mediaController.setAnchorView(binding?.vvRes)
            mediaController.setMediaPlayer(binding?.vvRes)
            binding?.vvRes?.setMediaController(mediaController)
            binding?.vvRes?.start()
        }
    }
}


