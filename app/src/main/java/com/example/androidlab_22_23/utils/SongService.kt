package com.example.androidlab_22_23.utils

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log

class SongService : Service() {

    private var mediaPlayer = MediaPlayer()

    inner class SongBinder : Binder() {

        fun playMusic(raw: Int) {
            play(raw)
        }

        fun pauseMusic() {
            pause()
        }

        fun stopMusic() {
            stop()
        }

        fun nextMusic(raw: Int) {
            next(raw)
        }

        fun previousMusic(raw: Int) {
            previous(raw)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.getParcelableExtra<MediaActions>("MEDIA_ACTION")?.let {
            when (it) {
                MediaActions.PLAY -> play(intent.getIntExtra("RAW", 15))
                MediaActions.PAUSE -> pause()
                MediaActions.STOP -> stop()
                MediaActions.PREVIOUS -> previous(intent.getIntExtra("PREVIOUS_RAW", 15))
                MediaActions.NEXT -> next(intent.getIntExtra("NEXT_RAW", 15))
            }
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder = SongBinder()

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    private fun play(raw: Int) {
        Log.e("SongService", "play")
        if (mediaPlayer.isPlaying) mediaPlayer.stop()
        mediaPlayer = MediaPlayer.create(applicationContext, raw)
        mediaPlayer.run {
            start()
            setOnCompletionListener {
                stop()
            }
        }
    }

    private fun pause() {
        mediaPlayer.pause()
    }

    private fun stop() {
        Log.e("SongService", "stop")
        mediaPlayer.stop()
    }

    private fun previous(raw: Int) {
        play(raw)
    }

    private fun next(raw: Int) {
        play(raw)
    }

}