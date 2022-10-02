package com.example.androidlab_22_23

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlab_22_23.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
//        val intent = Intent()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)?.also {
            setContentView(it.root)
        }

        binding?.btnSendMessage?.setOnClickListener {
//            val intent = Intent(this, ShareActivity::class.java)
            val intent = Intent(Intent.ACTION_SEND)
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, "Hello!")
            intent.type = "text/plan"
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }

        binding?.btnCall?.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:8005553535"))
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }

        binding?.btnDocumentation?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://developer.android.com/guide/components/intents-common"))
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
    }
}