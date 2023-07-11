package com.manoffocus.mfrickandmorty.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.manoffocus.mfrickandmorty.dao.MFRickAndMortyDao
import com.manoffocus.mfrickandmorty.dao.MFRickAndMortyDatabase
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyEpisodesRepository
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyLocationsRepository
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyRepositoryDatabase
import com.manoffocus.mfrickandmorty.screens.mfhome.MFHomeViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class MFHomeViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)
    @Inject
    lateinit var rickAndMortyDatabase: MFRickAndMortyDatabase
    private lateinit var rickAndMortyDao: MFRickAndMortyDao

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
        rickAndMortyDao = rickAndMortyDatabase.rickAndMortyDao()
        mfRickAndMortyRepositoryDatabase = MFRickAndMortyRepositoryDatabase(rickAndMortyDao)
        mfHomeViewModel = MFHomeViewModel(
            mfRickAndMortyRepositoryDatabase,
            mfRickAndMortyEpisodesRepository,
            mfRickAndMortyLocationsRepository
        )
    }

    @Test
    fun getLocationsByPageCode_ValueRequestTest(){
        runBlocking {
            val locations = mfHomeViewModel.locations.value
            mfHomeViewModel.getLocationsByPageCode(1)
        }
    }
}