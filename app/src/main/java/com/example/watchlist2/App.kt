package com.example.watchlist2

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp

@Suppress("unused")
@HiltAndroidApp
class App : Application(), ImageLoaderFactory {

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this@App)
            .availableMemoryPercentage(AVAILABLE_MEMORY_PERCENTAGE)
            .crossfade(CROSSFADE_DURATION_MILLIS)
            .build()
    }

    companion object {
        const val AVAILABLE_MEMORY_PERCENTAGE = 0.25
        const val CROSSFADE_DURATION_MILLIS = 750
    }
}
