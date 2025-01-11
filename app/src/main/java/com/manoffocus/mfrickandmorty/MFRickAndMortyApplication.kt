package com.manoffocus.mfrickandmorty

import android.app.Application
import com.manoffocus.mfrickandmorty.di.dataBaseModule
import com.manoffocus.mfrickandmorty.di.dispatchers
import com.manoffocus.mfrickandmorty.di.networkModule
import com.manoffocus.mfrickandmorty.di.viewViewModels
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MFRickAndMortyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MFRickAndMortyApplication)
            modules(listOf(networkModule, viewViewModels, dataBaseModule, dispatchers))
        }
    }
}