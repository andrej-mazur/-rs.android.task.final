package com.example.task6.exoplayer

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI
import android.support.v4.media.MediaMetadataCompat.METADATA_KEY_ARTIST
import android.support.v4.media.MediaMetadataCompat.METADATA_KEY_DURATION
import android.support.v4.media.MediaMetadataCompat.METADATA_KEY_MEDIA_ID
import android.support.v4.media.MediaMetadataCompat.METADATA_KEY_MEDIA_URI
import android.support.v4.media.MediaMetadataCompat.METADATA_KEY_TITLE
import androidx.core.net.toUri
import com.example.task6.data.TrackListing
import com.example.task6.exoplayer.State.STATE_CREATED
import com.example.task6.exoplayer.State.STATE_ERROR
import com.example.task6.exoplayer.State.STATE_INITIALIZED
import com.example.task6.exoplayer.State.STATE_INITIALIZING
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MusicSource @Inject constructor(
    private val trackListing: TrackListing
) {

    var tracks = emptyList<MediaMetadataCompat>()

    suspend fun fetchMediaData() = withContext(Dispatchers.IO) {
        state = STATE_INITIALIZING
        tracks = trackListing.getTracks().map { track ->
            MediaMetadataCompat.Builder()
                .putString(METADATA_KEY_MEDIA_ID, track.mediaId)
                .putString(METADATA_KEY_ARTIST, track.artist)
                .putString(METADATA_KEY_TITLE, track.title)
                .putString(METADATA_KEY_ALBUM_ART_URI, track.bitmapUri)
                .putString(METADATA_KEY_MEDIA_URI, track.trackUri)
                .putLong(METADATA_KEY_DURATION, track.duration)
                .build()
        }
        state = STATE_INITIALIZED
    }

    fun asMediaSource(dataSourceFactory: DefaultDataSourceFactory): ConcatenatingMediaSource {
        val concatenatingMediaSource = ConcatenatingMediaSource()
        tracks.forEach { track ->
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(track.getString(METADATA_KEY_MEDIA_URI).toUri())
            concatenatingMediaSource.addMediaSource(mediaSource)
        }
        return concatenatingMediaSource
    }

    fun asMediaItems() = tracks.map { track ->
        val desc = MediaDescriptionCompat.Builder()
            .setMediaUri(track.getString(METADATA_KEY_MEDIA_URI).toUri())
            .setTitle(track.description.title)
            .setSubtitle(track.description.subtitle)
            .setMediaId(track.description.mediaId)
            .setIconUri(track.description.iconUri)
            .build()
        MediaBrowserCompat.MediaItem(desc, FLAG_PLAYABLE)
    }.toMutableList()

    private val onReadyListeners = mutableListOf<(Boolean) -> Unit>()

    private var state: State = STATE_CREATED
        set(value) {
            if (value == STATE_INITIALIZED || value == STATE_ERROR) {
                synchronized(onReadyListeners) {
                    field = value
                    onReadyListeners.forEach { listener ->
                        listener(state == STATE_INITIALIZED)
                    }
                }
            } else {
                field = value
            }
        }

    fun whenReady(action: (Boolean) -> Unit): Boolean {
        return if (state == STATE_CREATED || state == STATE_INITIALIZING) {
            onReadyListeners += action
            false
        } else {
            action(state == STATE_INITIALIZED)
            true
        }
    }
}

enum class State {
    STATE_CREATED,
    STATE_INITIALIZING,
    STATE_INITIALIZED,
    STATE_ERROR
}















