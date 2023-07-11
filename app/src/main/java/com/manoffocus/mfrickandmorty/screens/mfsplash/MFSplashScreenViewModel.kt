package com.manoffocus.mfrickandmorty.screens.mfsplash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manoffocus.mfrickandmorty.data.ResourceUser
import com.manoffocus.mfrickandmorty.models.db.User
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyRepositoryDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MFSplashScreenViewModel
@Inject constructor(private val rickAndMortyRepositoryDatabase: MFRickAndMortyRepositoryDatabase) : ViewModel() {

    init {
        loadUsers()
    }
    private fun loadUsers(){
        viewModelScope.launch {
            rickAndMortyRepositoryDatabase.getAllUsers()
        }
    }
    fun getUsers(): StateFlow<ResourceUser<User>> {
        return rickAndMortyRepositoryDatabase.user
    }
    companion object {
        const val TAG = "MFSplashScreenViewModel"
    }
}