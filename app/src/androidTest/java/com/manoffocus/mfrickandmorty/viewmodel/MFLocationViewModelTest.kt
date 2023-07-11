package com.manoffocus.mfrickandmorty.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.manoffocus.mfrickandmorty.dao.MFRickAndMortyDao
import com.manoffocus.mfrickandmorty.dao.MFRickAndMortyDatabase
import com.manoffocus.mfrickandmorty.data.Resource
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyCharactersRepository
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyLocationsRepository
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyRepositoryDatabase
import com.manoffocus.mfrickandmorty.screens.mflocation.MFLocationViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class MFLocationViewModelTest {

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
    lateinit var mfRickAndMortyCharactersRepository: MFRickAndMortyCharactersRepository

    lateinit var mfLocationViewModel: MFLocationViewModel

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        rickAndMortyDao = rickAndMortyDatabase.rickAndMortyDao()
        mfRickAndMortyRepositoryDatabase = MFRickAndMortyRepositoryDatabase(rickAndMortyDao)
        mfLocationViewModel = MFLocationViewModel(
            mfRickAndMortyLocationsRepository,
            mfRickAndMortyCharactersRepository
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test(timeout = 20000)
    fun notNullLocationCharacters() = runTest {
        mfLocationViewModel.getLocationById(20)
        while (mfLocationViewModel.locationReq.value is Resource.Empty){ }
        mfLocationViewModel.locationReq.value.data?.let { data ->
            while (data.residentsFull == null){ }
            Assert.assertNotNull(mfLocationViewModel.locationReq.value.data?.residentsFull)
        }
    }
    @After
    fun closeDatabase() {
        rickAndMortyDatabase.close()
    }
    companion object {
        const val TAG = "MFLocationViewModelTest"
    }
}