package com.example.task6.exoplayer.extensions

import android.support.v4.media.MediaMetadataCompat
import com.example.task6.data.Track

fun MediaMetadataCompat.toTrack(): Track? {
    return description?.let {
        Track(
            it.mediaId ?: "",
            it.title.toString(),
            it.subtitle.toString(),
            it.iconUri.toString(),
            it.mediaUri.toString(),
            0L
        )
    }
}