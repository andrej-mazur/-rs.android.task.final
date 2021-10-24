package com.plcoding.spotifycloneyt.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.task6.R
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAssetManager(@ApplicationContext context: Context) =
        context.assets

    @Provides
    @Singleton
    fun provideMoshi() =
        Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideGlide(@ApplicationContext context: Context) =
        Glide.with(context).setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.ic_image_24)
                .error(R.drawable.ic_image_24)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
        )
}