package com.example.task6.ui

import android.support.v4.media.MediaBrowserCompat
import androidx.lifecycle.ViewModel
import com.example.task6.exoplayer.MusicServiceConnection
import com.example.task6.exoplayer.extensions.isPlayEnabled
import com.example.task6.exoplayer.extensions.isPlaying
import com.example.task6.other.Constants.MEDIA_ROOT_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val musicServiceConnection: MusicServiceConnection
) : ViewModel() {

    val isConnected = musicServiceConnection.isConnected
    val networkError = musicServiceConnection.networkError
    val curPlayingSong = musicServiceConnection.curPlayingSong
    val playbackState = musicServiceConnection.playbackState

    init {
        musicServiceConnection.subscribe(MEDIA_ROOT_ID, object : MediaBrowserCompat.SubscriptionCallback() {})
    }

    fun skipToNextSong() {
        musicServiceConnection.transportControls.skipToNext()
    }

    fun skipToPreviousSong() {
        musicServiceConnection.transportControls.skipToPrevious()
    }

    private fun play() {
        musicServiceConnection.transportControls.play()
    }

    private fun pause() {
        musicServiceConnection.transportControls.pause()
    }

    fun playOrPause() {
        playbackState.value?.let { playbackState ->
            when {
                playbackState.isPlaying -> pause()
                playbackState.isPlayEnabled -> play()
                else -> Unit
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        musicServiceConnection.unsubscribe(MEDIA_ROOT_ID, object : MediaBrowserCompat.SubscriptionCallback() {})
    }
}
