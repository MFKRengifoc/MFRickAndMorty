package com.manoffocus.mfrickandmorty.di

import android.content.Context
import androidx.room.Room
import com.manoffocus.mfrickandmorty.commons.Constants
import com.manoffocus.mfrickandmorty.dao.MFRickAndMortyDao
import com.manoffocus.mfrickandmorty.dao.MFRickAndMortyDatabase
import com.manoffocus.mfrickandmorty.network.RickAndMortyAPI
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyCharactersRepository
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyEpisodesRepository
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyLocationsRepository
import com.manoffocus.mfrickandmorty.services.MFNetworkConnectivityService
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@TestInstallIn(components = [SingletonComponent::class], replaces = [MFRickAndMortyDatabaseModule::class, MFNetworkModule::class])
@Module
class TestModule {
    @Singleton
    @Provides
    fun provideRickAndMortyDatabaseTest(@ApplicationContext ctx: Context): MFRickAndMortyDatabase {
        return Room.inMemoryDatabaseBuilder(
            ctx,
            MFRickAndMortyDatabase::class.java
        ).allowMainThreadQueries().build()
    }
    @Provides
    fun provideRickAndMortyDaoTest(rickAndMortyDatabase: MFRickAndMortyDatabase): MFRickAndMortyDao = rickAndMortyDatabase.rickAndMortyDao()

    @Singleton
    @Provides
    fun provideOkHttpClientTest(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS) // connect timeout
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }
    @Singleton
    @Provides
    fun provideRickAndMortyApiTest(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideRickAndMortyServiceTest(retrofit: Retrofit): RickAndMortyAPI {
        return retrofit.create(RickAndMortyAPI::class.java)
    }

    @Provides
    fun provideLocationsRepositoryTest(api: RickAndMortyAPI) = MFRickAndMortyLocationsRepository(api)

    @Provides
    fun provideCharactersRepositoryTest(api: RickAndMortyAPI) = MFRickAndMortyCharactersRepository(api)

    @Provides
    fun provideEpisodesRepositoryTest(api: RickAndMortyAPI) = MFRickAndMortyEpisodesRepository(api)

    @Provides
    fun provideConnectivityService(@ApplicationContext ctx: Context) = MFNetworkConnectivityService(ctx)

}