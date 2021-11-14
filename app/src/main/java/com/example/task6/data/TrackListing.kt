package com.example.task6.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrackListing @Inject constructor(
    trackLoader: TrackLoader
) {

    private var _tracks: List<Track>? = trackLoader.load()
    val tracks: List<Track> = requireNotNull(_tracks)
}
