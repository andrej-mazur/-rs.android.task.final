package com.example.task6.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Track(
    var mediaId: String?,
    val title: String,
    val artist: String,
    val bitmapUri: String,
    val trackUri: String,
    val duration: Long
)
