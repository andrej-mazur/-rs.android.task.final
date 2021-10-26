package com.example.task6.exoplayer.extensions

import android.support.v4.media.session.PlaybackStateCompat
import android.support.v4.media.session.PlaybackStateCompat.*

inline val PlaybackStateCompat.isPlaying
    get() = state == STATE_BUFFERING ||
            state == STATE_PLAYING

inline val PlaybackStateCompat.isPlayEnabled
    get() = (actions and ACTION_PLAY != 0L) ||
            (actions and ACTION_PLAY_PAUSE != 0L && state == STATE_PAUSED)