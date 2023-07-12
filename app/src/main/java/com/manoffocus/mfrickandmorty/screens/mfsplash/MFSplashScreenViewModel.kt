package com.manoffocus.mfrickandmorty.screens.mfsplash

import androidx.lifecycle.ViewModel
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyRepositoryDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MFSplashScreenViewModel
@Inject constructor(private val rickAndMortyRepositoryDatabase: MFRickAndMortyRepositoryDatabase) : ViewModel() {
    companion object {
        const val TAG = "MFSplashScreenViewModel"
    }
}