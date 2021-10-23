package com.example.task6.catalog

import com.example.task6.data.Track
import com.example.task6.loader.TrackLoader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrackListingImpl @Inject constructor(
    trackLoader: TrackLoader
) : TrackListing {

    private var _tracks: List<Track>? = trackLoader.load()
    private val tracks: List<Track> = requireNotNull(_tracks)

    override fun getTracks(): List<Track> = tracks
}