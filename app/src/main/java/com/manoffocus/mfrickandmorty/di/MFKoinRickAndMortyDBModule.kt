package com.manoffocus.mfrickandmorty.di

import android.content.Context
import androidx.room.Room
import com.manoffocus.mfrickandmorty.dao.MFRickAndMortyDao
import com.manoffocus.mfrickandmorty.dao.MFRickAndMortyDatabase
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyRepositoryDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


fun provideRickAndMortyDatabase(ctx: Context): MFRickAndMortyDatabase
        = Room.databaseBuilder(
    ctx,
    MFRickAndMortyDatabase::class.java,
    "rick_and_morty_database"
).fallbackToDestructiveMigration().build()


fun provideRickAndMortyDao(rickAndMortyDatabase: MFRickAndMortyDatabase): MFRickAndMortyDao = rickAndMortyDatabase.rickAndMortyDao()




val dataBaseModule = module {
    single { MFRickAndMortyRepositoryDatabase(rickAndMortyDao = get<MFRickAndMortyDao>()) }
    single { provideRickAndMortyDatabase(ctx = androidContext()) }
    single { provideRickAndMortyDao(rickAndMortyDatabase = get<MFRickAndMortyDatabase>()) }
}