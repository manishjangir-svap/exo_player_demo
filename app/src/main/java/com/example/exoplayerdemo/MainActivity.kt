package com.example.exoplayerdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsDataSourceFactory
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory

class MainActivity : AppCompatActivity() {
    private lateinit var playerView: PlayerView
    private lateinit var recyclerView: RecyclerView
    private lateinit var mediaItems: List<MediaItem>
    private lateinit var simpleExoPlayer: SimpleExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playerView = findViewById(R.id.playerView)
        recyclerView = findViewById(R.id.recyclerView)

        simpleExoPlayer = SimpleExoPlayer.Builder(this).build()
        simpleExoPlayer.addListener(ExoPlayerListener(this, simpleExoPlayer))
        playerView.player = simpleExoPlayer

        // Can use MediaItems
        //simpleExoPlayer.setMediaItems(getMediaItems())

        // Can use MediaSource
        simpleExoPlayer.setMediaSources(getMediaSources())

        simpleExoPlayer.playWhenReady = true
        simpleExoPlayer.prepare()
        simpleExoPlayer.play()
    }

    private fun getMediaItems(): List<MediaItem> = run {
        arrayListOf(
            MediaItem.fromUri("http://demo.unified-streaming.com/video/tears-of-steel/tears-of-steel.ism/.m3u8"),
            MediaItem.fromUri("https://www.radiantmediaplayer.com/media/big-buck-bunny-360p.mp4"),
            MediaItem.fromUri("http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8")
        )
    }

    private fun getMediaSources(): List<MediaSource> = run {
        val mediaSource = createHlsMediaSource("http://demo.unified-streaming.com/video/tears-of-steel/tears-of-steel.ism/.m3u8")

        arrayListOf(
            createHlsMediaSource("http://demo.unified-streaming.com/video/tears-of-steel/tears-of-steel.ism/.m3u8"),
            createProgressiveMediaSource("https://www.radiantmediaplayer.com/media/big-buck-bunny-360p.mp4"),
            createHlsMediaSource("http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8")
        )
    }

    private fun createHlsMediaSource(url: String): MediaSource = run {
        val dataSourceFactory = DefaultHttpDataSourceFactory()
        val mediaSource = HlsMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(url))

        mediaSource
    }

    private fun createProgressiveMediaSource(url: String): MediaSource = run {
        val dataSourceFactory = DefaultHttpDataSourceFactory()
        val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(url))

        mediaSource
    }
}