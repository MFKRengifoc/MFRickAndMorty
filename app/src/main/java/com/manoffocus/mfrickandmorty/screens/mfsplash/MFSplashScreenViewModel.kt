package com.manoffocus.mfrickandmorty.screens.mfsplash

import androidx.lifecycle.ViewModel
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyRepositoryDatabase

class MFSplashScreenViewModel(private val rickAndMortyRepositoryDatabase: MFRickAndMortyRepositoryDatabase) : ViewModel() {
    companion object {
        const val TAG = "MFSplashScreenViewModel"
    }
}