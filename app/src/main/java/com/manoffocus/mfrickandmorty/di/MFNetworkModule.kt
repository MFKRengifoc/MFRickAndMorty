package com.manoffocus.mfrickandmorty.di

import android.content.Context
import com.manoffocus.mfrickandmorty.commons.Constants
import com.manoffocus.mfrickandmorty.network.RickAndMortyAPI
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyCharactersRepository
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyEpisodesRepository
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyLocationsRepository
import com.manoffocus.mfrickandmorty.services.MFNetworkConnectivityService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MFNetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS) // connect timeout
            .readTimeout(15, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Singleton
    @Provides
    fun provideRickAndMortyApi(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideRickAndMortyService(retrofit: Retrofit): RickAndMortyAPI {
        return retrofit.create(RickAndMortyAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideLocationsRepository(api: RickAndMortyAPI) = MFRickAndMortyLocationsRepository(api)

    @Singleton
    @Provides
    fun provideCharactersRepository(api: RickAndMortyAPI) = MFRickAndMortyCharactersRepository(api)

    @Singleton
    @Provides
    fun provideEpisodesRepository(api: RickAndMortyAPI) = MFRickAndMortyEpisodesRepository(api)

    @Singleton
    @Provides
    fun provideConnectivityService(@ApplicationContext ctx: Context) = MFNetworkConnectivityService(ctx)
}