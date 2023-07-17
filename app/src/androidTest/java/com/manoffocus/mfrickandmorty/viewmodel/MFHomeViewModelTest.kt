package com.manoffocus.mfrickandmorty.viewmodel

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.manoffocus.mfrickandmorty.dao.MFRickAndMortyDatabase
import com.manoffocus.mfrickandmorty.data.Resource
import com.manoffocus.mfrickandmorty.models.locations.LocationsRequest
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyEpisodesRepository
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyLocationsRepository
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyRepositoryDatabase
import com.manoffocus.mfrickandmorty.screens.mfhome.MFHomeViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import javax.inject.Inject

@HiltAndroidTest
class MFHomeViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)
    @Inject
    lateinit var rickAndMortyDatabase: MFRickAndMortyDatabase

    @Inject
    lateinit var mfRickAndMortyRepositoryDatabase: MFRickAndMortyRepositoryDatabase
    @Inject
    lateinit var mfRickAndMortyLocationsRepository: MFRickAndMortyLocationsRepository
    @Inject
    lateinit var mfRickAndMortyEpisodesRepository: MFRickAndMortyEpisodesRepository

    lateinit var mfHomeViewModel: MFHomeViewModel

    @Before
    fun setup(){
        hiltAndroidRule.inject()
        mfHomeViewModel = MFHomeViewModel(
            mfRickAndMortyRepositoryDatabase,
            mfRickAndMortyEpisodesRepository,
            mfRickAndMortyLocationsRepository
        )
    }

    @Test
    fun getLocationsByPageCode_ValueRequestTest() = runTest {
        val locations = mfHomeViewModel.locationReq.value
        `when`(locations).thenReturn(Resource.Success(data = LocationsRequest()))
        mfHomeViewModel.getLocationsByPageCode(1)
        Log.d(TAG, "getLocationsByPageCode_ValueRequestTest: ${locations.data}")
    }
    companion object {
        const val TAG = "MFHomeViewModelTest"
    }
}