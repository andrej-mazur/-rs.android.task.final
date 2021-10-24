package com.example.task6.data

import android.content.res.AssetManager
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrackLoader @Inject constructor(
    private val assetManager: AssetManager,
    private val moshi: Moshi
) {

    fun load(): List<Track>? {
        val type = Types.newParameterizedType(List::class.java, Track::class.java)
        val adapter: JsonAdapter<List<Track>> = moshi.adapter(type)
        val jsonText = assetManager.open(FILE_NAME).bufferedReader().use { it.readText() }
        return adapter.fromJson(jsonText)
    }

    companion object {
        private const val FILE_NAME = "playlist.json"
    }
}