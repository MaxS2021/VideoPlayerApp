package com.smastcomm.videoplayerapp

import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.smastcomm.videoplayerapp.databinding.ActivityWatchBinding
import java.util.SimpleTimeZone

class WatchActivity : AppCompatActivity() {

    lateinit var binding: ActivityWatchBinding
    var isFullScreen = false
    var isLock = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btFullScreen = findViewById<ImageView>(R.id.btFullscreen)
        val btLockScreen = findViewById<ImageView>(R.id.exo_lock)

        btFullScreen.setOnClickListener {
            if(!isFullScreen) {
                btFullScreen.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_fullscreen_exit))
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE)
            }
            else {
                btFullScreen.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_fullscreen))
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            }
            isFullScreen = !isFullScreen
        }

        btLockScreen.setOnClickListener {
            if(!isLock) {
                btLockScreen.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_lock))

            }
            else {
                btLockScreen.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_lock_open))

            }
            isLock = !isLock
            lockScreen(isLock)
        }

        val simpleExoPlayer = ExoPlayer.Builder(this)
            .setSeekBackIncrementMs(5000)
            .setSeekForwardIncrementMs(5000)
            .build()
        binding.player.player = simpleExoPlayer
        binding.player.keepScreenOn = true
        simpleExoPlayer.addListener(object : Player.Listener{
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if(playbackState == Player.STATE_BUFFERING) {
                    binding.progressBar.visibility = View.VISIBLE
                }
                else if(playbackState == Player.STATE_READY){
                    binding.progressBar.visibility = View.GONE

                }
            }
        })
        val videoSource = Uri.parse("https:/www.rmp-streaming.com/media/big-buck-bunny-360p.mp4")
        val mediaItem = MediaItem.fromUri(videoSource)
        simpleExoPlayer.setMediaItem(mediaItem)
        simpleExoPlayer.prepare()
        simpleExoPlayer.play()


    }

    private fun lockScreen(lock: Boolean) {
        val sec_mid = findViewById<LinearLayout>(R.id.sec_controlvid1)
        val sec_bottom = findViewById<LinearLayout>(R.id.sec_controlvid2)
        if(lock){
            sec_mid.visibility = View.INVISIBLE
            sec_bottom.visibility = View.INVISIBLE
        } else {
            sec_mid.visibility = View.VISIBLE
            sec_bottom.visibility = View.VISIBLE
        }

    }
}