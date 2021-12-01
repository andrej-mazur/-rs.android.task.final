package com.example.watchlist2.di

import com.example.watchlist2.common.Constants.BASE_URL
import com.example.watchlist2.data.remote.JikanApi
import com.example.watchlist2.data.remote.interceptor.RateLimiterInterceptor
import com.example.watchlist2.data.repository.AnimeRepository
import com.example.watchlist2.data.repository.AnimeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideJikanApi(): JikanApi {
        val client = OkHttpClient.Builder()
            .addInterceptor(RateLimiterInterceptor())
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(JikanApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAnimeRepository(api: JikanApi): AnimeRepository {
        return AnimeRepositoryImpl(api)
    }
}