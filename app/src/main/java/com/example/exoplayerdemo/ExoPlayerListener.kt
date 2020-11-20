package com.example.exoplayerdemo

import android.content.Context
import android.widget.Toast
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer

class ExoPlayerListener(private val context: Context, private val simpleExoPlayer: SimpleExoPlayer): Player.EventListener {
    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)
        Toast.makeText(context, "Test Video: ${if(isPlaying) "Playing" else "Paused"}", Toast.LENGTH_SHORT).show()

        // Working for aspect ratio of the player here...
        println(simpleExoPlayer.videoFormat?.height)
    }
}