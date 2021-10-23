package com.example.task6.di

import com.example.task6.catalog.TrackListing
import com.example.task6.catalog.TrackListingImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class TrackListingModule {

    @Binds
    @Singleton
    abstract fun bindTrackListing(trackListingImpl: TrackListingImpl): TrackListing
}