package com.example.watchlist2.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.watchlist2.common.Constants.BASE_URL
import com.example.watchlist2.data.remote.JikanApi
import com.example.watchlist2.data.remote.interceptor.RateLimiterInterceptor
import com.example.watchlist2.data.repository.AnimeRepository
import com.example.watchlist2.data.repository.AnimeRepositoryImpl
import com.example.watchlist2.util.PreferencesWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideJikanApi(): JikanApi {
        val logging = HttpLoggingInterceptor().apply {
            level = Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(RateLimiterInterceptor())
            .addInterceptor(logging)
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

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    @Singleton
    fun providePreferencesWrapper(sharedPreferences: SharedPreferences): PreferencesWrapper {
        return PreferencesWrapper(sharedPreferences)
    }
}