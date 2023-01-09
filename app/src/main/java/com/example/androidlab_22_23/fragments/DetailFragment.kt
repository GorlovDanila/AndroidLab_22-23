package com.example.androidlab_22_23.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.*
import android.os.*
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.example.androidlab_22_23.utils.MediaActions
import com.example.androidlab_22_23.R
import com.example.androidlab_22_23.utils.SongService
import com.example.androidlab_22_23.databinding.FragmentDetailBinding
import com.example.androidlab_22_23.model.Song
import com.example.androidlab_22_23.model.SongRepository
import com.example.androidlab_22_23.utils.CreateNotification
import com.example.androidlab_22_23.utils.OnClearFromRecentService

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private var binding: FragmentDetailBinding? = null
    private var count: Int = 0
    var binder: SongService.SongBinder? = null
    private var prevIdFromBundle: Long? = null
    private var idFromBundle: Long? = null
    private var nextIdFromBundle: Long? = null
    private lateinit var prevSong: Song
    private lateinit var song: Song
    private lateinit var nextSong: Song
    private var notificationManager: NotificationManager? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            binder = service as? SongService.SongBinder
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            binder = null
        }
    }

    private var broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            when (intent?.getStringExtra("ACTION_NAME")) {
                MediaActions.PREVIOUS.toString() -> previous()
                MediaActions.PLAY.toString() -> playPause()
                MediaActions.NEXT.toString() -> next()
                MediaActions.STOP.toString() -> stop()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
            requireContext().registerReceiver(broadcastReceiver, IntentFilter("TRACKS_TRACKS"))
            requireContext().startService(
                Intent(
                    requireContext(),
                    OnClearFromRecentService::class.java
                )
            )
        }
        prevIdFromBundle = arguments?.getLong(ARG_PREV_ID_VALUE)
        idFromBundle = arguments?.getLong(ARG_ID_VALUE)
        nextIdFromBundle = arguments?.getLong(ARG_NEXT_ID_VALUE)
        Log.e(
            "FIRST_IDS",
            prevIdFromBundle.toString() + " " + idFromBundle.toString() + " " + nextIdFromBundle
        )
        checkIds(prevIdFromBundle, idFromBundle, nextIdFromBundle)
        Log.e(
            "SECOND_IDS",
            prevIdFromBundle.toString() + " " + idFromBundle.toString() + " " + nextIdFromBundle
        )
        binding = FragmentDetailBinding.bind(view)

        binding?.run {
            ivCover.setImageResource(song.cover)
            tvTitle.text = song.title
            tvAuthor.text = song.author
            tvAlbum.text = song.album
//            if(count == 0) {
            init(song)
//            }
            ivPlay.setOnClickListener {
                playPause()
            }
            ivStop.setOnClickListener {
                stop()
            }
            ivPrev.setOnClickListener {
                previous()
            }
            ivNext.setOnClickListener {
                next()
            }

            skStatus.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) SongService().mediaPlayer.seekTo(progress)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }
            })
        }
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CreateNotification().channelId,
                "Music", NotificationManager.IMPORTANCE_LOW
            )
            notificationManager = requireContext().getSystemService(NotificationManager::class.java)
            if (notificationManager != null) {
                notificationManager!!.createNotificationChannel(channel)
            }
        }
    }

    private fun init(song: Song) {

        requireContext().bindService(
            Intent(requireContext(), SongService::class.java).apply {
                putExtra("MEDIA_ACTION", MediaActions.PLAY as Parcelable)
                putExtra("RAW", song.audio)
            },
            connection,
            Service.BIND_AUTO_CREATE
        )

        binder?.playMusic(song.audio)
        requireContext().bindService(
            Intent(requireContext(), SongService::class.java).apply {
                putExtra("MEDIA_ACTION", MediaActions.PAUSE as Parcelable)
            },
            connection,
            Service.BIND_AUTO_CREATE
        )
        binder?.pauseMusic()
    }

    private fun playPause() {
        binding.run {
            count++
            if (count % 2 == 1) {
                song.isPlaying = true
                CreateNotification().createNotification(
                    requireContext(),
                    song,
                    R.drawable.ic_baseline_pause_circle_24,
                )
                binding?.ivPlay?.setImageResource(R.drawable.ic_baseline_pause_circle_24)

                requireContext().bindService(
                    Intent(requireContext(), SongService::class.java).apply {
                        putExtra("MEDIA_ACTION", MediaActions.PLAY as Parcelable)
                        putExtra("RAW", song.audio)
                    },
                    connection,
                    Service.BIND_AUTO_CREATE
                )

                binder?.playMusic(song.audio)
                Log.e("MP", SongService().mediaPlayer.toString())
                initialiseSeekBer()
            } else {
                song.isPlaying = false
                CreateNotification().createNotification(
                    requireContext(),
                    song,
                    R.drawable.ic_baseline_play_circle_24,
                )
                binding?.ivPlay?.setImageResource(R.drawable.ic_baseline_play_circle_24)
                requireContext().bindService(
                    Intent(requireContext(), SongService::class.java).apply {
                        putExtra("MEDIA_ACTION", MediaActions.PAUSE as Parcelable)
                        //                        putExtra("RAW", song.audio)
                    },
                    connection,
                    Service.BIND_AUTO_CREATE
                )
                binder?.pauseMusic()
            }
        }
    }

    private fun stop() {
        song.isPlaying = false
        requireContext().bindService(
            Intent(requireContext(), SongService::class.java).apply {
                putExtra("MEDIA_ACTION", MediaActions.STOP as Parcelable)
            },
            connection,
            Service.BIND_AUTO_CREATE
        )
        binder?.stopMusic()
        parentFragmentManager.beginTransaction().setCustomAnimations(
            com.google.android.material.R.anim.abc_fade_in,
            com.google.android.material.R.anim.abc_fade_out,
            com.google.android.material.R.anim.abc_fade_in,
            com.google.android.material.R.anim.abc_fade_out
        )
            .replace(R.id.fragment_container, MainFragment())
            .commit()
        notificationManager?.cancelAll()
    }

    private fun previous() {
        count++
        CreateNotification().createNotification(
            requireContext(),
            prevSong,
            R.drawable.ic_baseline_pause_circle_24,
        )

        requireContext().bindService(
            Intent(requireContext(), SongService::class.java).apply {
                putExtra("MEDIA_ACTION", MediaActions.PREVIOUS as Parcelable)
            },
            connection,
            Service.BIND_AUTO_CREATE
        )
        binding?.ivPlay?.setImageResource(R.drawable.ic_baseline_pause_circle_24)
        binding?.ivCover?.setImageResource(prevSong.cover)
        binding?.tvTitle?.text = prevSong.title
        binding?.tvAuthor?.text = prevSong.author
        binding?.tvAlbum?.text = prevSong.album
        binder?.previousMusic(prevSong.audio)
        nextIdFromBundle = idFromBundle
        idFromBundle = prevIdFromBundle
        prevIdFromBundle = idFromBundle?.minus(1)
        Log.e(
            "PREV_IDS",
            prevIdFromBundle.toString() + " " + idFromBundle.toString() + " " + nextIdFromBundle
        )
        checkIds(prevIdFromBundle, idFromBundle, nextIdFromBundle)
        Log.e(
            "PREV_IDS_2",
            prevIdFromBundle.toString() + " " + idFromBundle.toString() + " " + nextIdFromBundle
        )
    }

    private fun next() {
        count++
        Log.e("CONTEXT", context.toString())
        CreateNotification().createNotification(
            requireContext(),
            nextSong,
            R.drawable.ic_baseline_pause_circle_24,
        )
        binding.run {
            requireContext().bindService(
                Intent(requireContext(), SongService::class.java).apply {
                    putExtra("MEDIA_ACTION", MediaActions.NEXT as Parcelable)
                },
                connection,
                Service.BIND_AUTO_CREATE
            )
            binding?.ivPlay?.setImageResource(R.drawable.ic_baseline_pause_circle_24)
            binding?.ivCover?.setImageResource(nextSong.cover)
            binding?.tvTitle?.text = nextSong.title
            binding?.tvAuthor?.text = nextSong.author
            binding?.tvAlbum?.text = nextSong.album
            binder?.nextMusic(nextSong.audio)

            prevIdFromBundle = idFromBundle
            idFromBundle = nextIdFromBundle
            nextIdFromBundle = nextIdFromBundle!! + 1
            Log.e(
                "NEXT_IDS",
                prevIdFromBundle.toString() + " " + idFromBundle.toString() + " " + nextIdFromBundle
            )
            checkIds(prevIdFromBundle, idFromBundle, nextIdFromBundle)
            Log.e(
                "NEXT_IDS_2",
                prevIdFromBundle.toString() + " " + idFromBundle.toString() + " " + nextIdFromBundle
            )
        }
    }

    private fun checkIds(prevId: Long?, id: Long?, nextId: Long?) {
        if (prevId != null) {
            if (prevId < 0L)
                prevIdFromBundle = SongRepository.songsList.size.toLong() - 1
        }

        if (nextId != null) {
            if (nextId >= SongRepository.songsList.size) {
                nextIdFromBundle = 0
            }
        }
        prevSong = SongRepository.songsList[prevIdFromBundle!!.toInt()]
        song = SongRepository.songsList[idFromBundle!!.toInt()]
        nextSong = SongRepository.songsList[nextIdFromBundle!!.toInt()]
    }

    private fun initialiseSeekBer() {
        binding?.run {

            skStatus.max = SongService().mediaPlayer.duration

            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed(object : Runnable {
                override fun run() {
                    try {
                        skStatus.progress = SongService().mediaPlayer.currentPosition
                        handler.postDelayed(this, 1000)
                    } catch (e: Exception) {
                        Log.e("PROGRESS", "0")
                        skStatus.progress = 0
                    }
                }
            }, 0)
        }
    }

    companion object {
        private const val ARG_PREV_ID_VALUE = "arg_prev_id_value"
        private const val ARG_ID_VALUE = "arg_id_value"
        private const val ARG_NEXT_ID_VALUE = "arg_next_id_value"

        fun newInstance(prevId: Long, id: Long, nextId: Long) = DetailFragment().apply {
            arguments = Bundle().apply {
                putLong(ARG_PREV_ID_VALUE, prevId)
                putLong(ARG_ID_VALUE, id)
                putLong(ARG_NEXT_ID_VALUE, nextId)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}