package com.example.task5

import android.app.Application
import android.content.Context
import android.os.Build.VERSION.SDK_INT
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.util.CoilUtils
import com.example.task5.api.CatApi
import com.example.task5.datasource.CatPagingRepository
import com.example.task5.datasource.CatService
import com.example.task5.di.ServiceLocator
import okhttp3.OkHttpClient

class App : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()

        ServiceLocator.register<Context>(this@App)
        ServiceLocator.register(CatApi.create())
        ServiceLocator.register(CatService(ServiceLocator.locate()))
        ServiceLocator.register(CatPagingRepository(ServiceLocator.locate()))
        val okHttpClient = OkHttpClient.Builder()
            .cache(CoilUtils.createDefaultCache(ServiceLocator.locate()))
            .build()
        ServiceLocator.register(okHttpClient)
    }

    override fun newImageLoader(): ImageLoader {
        val okHttpClient = ServiceLocator.locate<OkHttpClient>()
        return ImageLoader.Builder(applicationContext)
            .componentRegistry {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder(applicationContext))
                } else {
                    add(GifDecoder())
                }
            }
            .availableMemoryPercentage(0.25)
            .crossfade(true)
            .okHttpClient(okHttpClient)
            .build()
    }
}
