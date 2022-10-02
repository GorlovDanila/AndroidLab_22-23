package com.example.androidlab_22_23

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity

class ShareActivity : AppCompatActivity() {
    private var binding: com.example.androidlab_22_23.databinding.ActivityShareBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val intent = Intent()
//        val intent = intent
//        Log.e("intent", intent.toString())
        val action: String? = intent.action
        val actionType = intent.type

//        if(action != null) {
//            Log.e("action", action)
//        }
//        Log.e("actionType", "$actionType")
//        if(actionType != null) {
//
//        }
        binding =
            com.example.androidlab_22_23.databinding.ActivityShareBinding.inflate(layoutInflater)
                .also {
                    setContentView(it.root)
                }
        if (action == Intent.ACTION_SEND) {
//
            binding?.tvResult?.text = intent.getStringExtra(Intent.EXTRA_TEXT)

            setContentView(R.layout.activity_share)

            binding =
                com.example.androidlab_22_23.databinding.ActivityShareBinding.inflate(layoutInflater)
                    .also {
                        setContentView(it.root)
                    }
            if (action != null && action == Intent.ACTION_SEND && actionType != null && actionType == "text/plain") {
//            Log.e("action", "true")
                binding?.tvResult?.text = intent.getStringExtra(Intent.ACTION_GET_CONTENT)
//            Log.d("res", binding?.tvResult?.text as String)
                if (action != null && action == Intent.ACTION_VIEW) {
                    //binding?.ivRes?.text = intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM)
                }
            }
        }
    }
}


