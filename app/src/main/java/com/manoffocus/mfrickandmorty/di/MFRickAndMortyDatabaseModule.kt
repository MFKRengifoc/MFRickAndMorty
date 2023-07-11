package com.manoffocus.mfrickandmorty.di

import android.content.Context
import androidx.room.Room
import com.manoffocus.mfrickandmorty.dao.MFRickAndMortyDao
import com.manoffocus.mfrickandmorty.dao.MFRickAndMortyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MFRickAndMortyDatabaseModule {

    @Singleton
    @Provides
    fun provideRickAndMortyDatabase(@ApplicationContext ctx: Context): MFRickAndMortyDatabase
            = Room.databaseBuilder(
        ctx,
        MFRickAndMortyDatabase::class.java,
        "rick_and_morty_database"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideRickAndMortyDao(rickAndMortyDatabase: MFRickAndMortyDatabase): MFRickAndMortyDao = rickAndMortyDatabase.rickAndMortyDao()
}