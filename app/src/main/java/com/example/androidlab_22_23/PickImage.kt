package com.example.androidlab_22_23

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Parcelable
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.net.toUri
import java.io.FileDescriptor

class PickImage : ActivityResultContract<Void?, Bitmap?>() {

    private var myContext: Context? = null

    override fun createIntent(context: Context, input: Void?): Intent {
        myContext = context
        val galleryIntent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val chooserIntent = Intent.createChooser(galleryIntent, "Choose")
        chooserIntent?.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
        return chooserIntent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Bitmap? {
        val bitmap =
            if (resultCode == Activity.RESULT_OK && intent?.getParcelableExtra<Parcelable>("data") == null) {
                val parcelFileDescriptor =
                    myContext?.contentResolver?.openFileDescriptor(
                        intent?.data.toString().toUri(),
                        "r"
                    )
                val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
                val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
                parcelFileDescriptor.close()
                image
            } else {
                intent?.getParcelableExtra("data")
            }
        return bitmap
    }
}