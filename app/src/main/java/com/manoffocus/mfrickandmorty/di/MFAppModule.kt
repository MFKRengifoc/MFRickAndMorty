package com.manoffocus.mfrickandmorty.di

import com.manoffocus.mfrickandmorty.activities.mainactivity.MainViewModel
import com.manoffocus.mfrickandmorty.commons.Constants
import com.manoffocus.mfrickandmorty.di.MFAppModule.DEFAULT_DISPATCHER
import com.manoffocus.mfrickandmorty.di.MFAppModule.IO_DISPATCHER
import com.manoffocus.mfrickandmorty.di.MFAppModule.MAIN_DISPATCHER
import com.manoffocus.mfrickandmorty.network.RickAndMortyAPI
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyCharactersRepository
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyEpisodesRepository
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyLocationsRepository
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyRepositoryDatabase
import com.manoffocus.mfrickandmorty.screens.mfcharacter.MFCharacterViewModel
import com.manoffocus.mfrickandmorty.screens.mfhome.MFHomeViewModel
import com.manoffocus.mfrickandmorty.screens.mflocation.MFAllLocationsViewModel
import com.manoffocus.mfrickandmorty.screens.mflocation.MFLocationViewModel
import com.manoffocus.mfrickandmorty.screens.mfprofiler.MFProfilerViewModel
import com.manoffocus.mfrickandmorty.screens.mfquiz.MFQuizViewModel
import com.manoffocus.mfrickandmorty.screens.mfsearch.MFSearchViewModel
import com.manoffocus.mfrickandmorty.screens.mfseason.MFSeasonViewModel
import com.manoffocus.mfrickandmorty.screens.mfuserprofile.MFUserProfileViewModel
import com.manoffocus.mfrickandmorty.services.MFNetworkConnectivityService
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object MFAppModule {
    const val IO_DISPATCHER = "IODispatcher"
    const val MAIN_DISPATCHER = "MainDispatcher"
    const val DEFAULT_DISPATCHER = "DefaultDispatcher"
}

val networkModule = module {
    single<RickAndMortyAPI> {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS) // connect timeout
                    .readTimeout(15, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build()
            )
            .build()
            .create(RickAndMortyAPI::class.java)
    }

    single { MFRickAndMortyLocationsRepository(api = get<RickAndMortyAPI>()) }
    single { MFRickAndMortyCharactersRepository(api = get<RickAndMortyAPI>()) }
    single { MFRickAndMortyEpisodesRepository(api = get<RickAndMortyAPI>()) }
    single { MFNetworkConnectivityService(context = androidContext()) }
}

val dispatchers = module {
    single(named(IO_DISPATCHER)) { Dispatchers.IO }
    single(named(MAIN_DISPATCHER)) { Dispatchers.Default }
    single(named(DEFAULT_DISPATCHER)) { Dispatchers.Main }
}

val viewViewModels = module {
    viewModel {
        MainViewModel(
            connectivityService = get<MFNetworkConnectivityService>(),
            rickAndMortyRepositoryDatabase = get<MFRickAndMortyRepositoryDatabase>()
        )
    }
    viewModel {
        MFHomeViewModel(
            rickAndMortyRepositoryDatabase = get<MFRickAndMortyRepositoryDatabase>(),
            episodesRepository = get<MFRickAndMortyEpisodesRepository>(),
            locationsRepository = get<MFRickAndMortyLocationsRepository>(),
            ioDispatcher = get(named(IO_DISPATCHER))
        )
    }
    viewModel {
        MFCharacterViewModel(
            rickAndMortyRepositoryDatabase = get<MFRickAndMortyRepositoryDatabase>(),
            episodesRepository = get<MFRickAndMortyEpisodesRepository>(),
            charactersRepository = get<MFRickAndMortyCharactersRepository>()
        )
    }
    viewModel {
        MFLocationViewModel(
            mfRickAndMortyLocationsRepository = get<MFRickAndMortyLocationsRepository>(),
            mfRickAndMortyCharactersRepository = get<MFRickAndMortyCharactersRepository>()
        )
    }
    viewModel {
        MFAllLocationsViewModel(
            locationsRepository = get<MFRickAndMortyLocationsRepository>(),
            ioDispatcher = get(named(IO_DISPATCHER))
        )
    }
    viewModel {
        MFLocationViewModel(
            mfRickAndMortyLocationsRepository = get<MFRickAndMortyLocationsRepository>(),
            mfRickAndMortyCharactersRepository = get<MFRickAndMortyCharactersRepository>()
        )
    }
    viewModel {
        MFSeasonViewModel(
            episodesRepository = get<MFRickAndMortyEpisodesRepository>(),
            charactersRepository = get<MFRickAndMortyCharactersRepository>()
        )
    }
    viewModel {
        MFSearchViewModel(
            charactersRepository = get<MFRickAndMortyCharactersRepository>(),
            ioDispatcher = get(named(IO_DISPATCHER))
        )
    }
    viewModel {
        MFUserProfileViewModel(
            mfRickAndMortyRepositoryDatabase = get<MFRickAndMortyRepositoryDatabase>()
        )
    }
    viewModel {
        MFQuizViewModel(
            mfRickAndMortyRepositoryDatabase = get<MFRickAndMortyRepositoryDatabase>()
        )
    }
    viewModel {
        MFProfilerViewModel(
            charactersRepository = get<MFRickAndMortyCharactersRepository>(),
            rickAndMortyRepositoryDatabase = get<MFRickAndMortyRepositoryDatabase>()
        )
    }
}