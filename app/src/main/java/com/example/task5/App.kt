package com.example.task5

import android.app.Application
import android.content.Context
import com.example.task5.api.CatApi
import com.example.task5.di.ServiceLocator
import com.example.task5.datasource.CatPagingRepository
import com.example.task5.datasource.CatService

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        ServiceLocator.register<Context>(this)
        ServiceLocator.register(CatApi.create())
        ServiceLocator.register(CatService(ServiceLocator.locate()))
        ServiceLocator.register(CatPagingRepository(ServiceLocator.locate()))
    }
}
