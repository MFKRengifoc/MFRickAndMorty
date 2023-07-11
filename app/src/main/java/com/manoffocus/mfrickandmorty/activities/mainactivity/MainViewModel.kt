package com.manoffocus.mfrickandmorty.activities.mainactivity

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manoffocus.mfrickandmorty.data.NetworkStatus
import com.manoffocus.mfrickandmorty.data.ResourceUser
import com.manoffocus.mfrickandmorty.models.db.User
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyRepositoryDatabase
import com.manoffocus.mfrickandmorty.services.MFNetworkConnectivityService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val connectivityService: MFNetworkConnectivityService,
    private val rickAndMortyRepositoryDatabase: MFRickAndMortyRepositoryDatabase
) : ViewModel() {
    val user : MutableState<ResourceUser<User>> = mutableStateOf(ResourceUser.Empty())
    private val _networkStatus = connectivityService.networkStatus.stateIn(
        scope = viewModelScope,
        initialValue = NetworkStatus.Unknown,
        started = WhileSubscribed(5000)
    )
    val networkStatus : MutableState<Pair<String, Boolean>> = mutableStateOf(Pair("", false))
    private fun loadUser(){
        viewModelScope.launch {
            rickAndMortyRepositoryDatabase.getAllUsers()
        }
    }
    private fun collectUser(){
        viewModelScope.launch {
            rickAndMortyRepositoryDatabase.user.collect { userRes ->
                user.value = userRes
            }
        }
    }
    fun load(){
        loadUser()
        collectUser()
    }
    fun getNetworkStatus(ctx: Context){
        viewModelScope.launch {
            _networkStatus.collect { nS ->
                networkStatus.value = nS.getStatus(ctx)
            }
        }
    }
}