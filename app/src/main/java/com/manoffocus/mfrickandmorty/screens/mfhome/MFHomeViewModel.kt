package com.manoffocus.mfrickandmorty.screens.mfhome

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manoffocus.mfrickandmorty.data.Resource
import com.manoffocus.mfrickandmorty.models.db.CharacterLike
import com.manoffocus.mfrickandmorty.models.episodes.EpisodesRequest
import com.manoffocus.mfrickandmorty.models.episodes.MFEpisode
import com.manoffocus.mfrickandmorty.models.locations.LocationsRequest
import com.manoffocus.mfrickandmorty.models.locations.MFLocation
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyEpisodesRepository
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyLocationsRepository
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyRepositoryDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MFHomeViewModel @Inject constructor(
    private val rickAndMortyRepositoryDatabase: MFRickAndMortyRepositoryDatabase,
    private val episodesRepository: MFRickAndMortyEpisodesRepository,
    private val locationsRepository: MFRickAndMortyLocationsRepository,
    ): ViewModel() {
    val likes : MutableState<List<CharacterLike>> = mutableStateOf(emptyList())
    val locationReq : MutableState<Resource<LocationsRequest>> = mutableStateOf(Resource.Empty())
    var locations : MutableState<List<MFLocation>> = mutableStateOf(emptyList())
    val seasonReq : MutableState<Resource<EpisodesRequest>> = mutableStateOf(Resource.Empty())
    val seasons : MutableState<List<MFEpisode>> = mutableStateOf(emptyList())

    private fun getLikes(){
        viewModelScope.launch(Dispatchers.IO) {
            rickAndMortyRepositoryDatabase.getAllLikes()
        }
    }
    fun getSeasonsByFirstEpisodeCode(code: String){
        viewModelScope.launch(Dispatchers.IO) {
            seasonReq.value = Resource.Loading(loading = true)
            episodesRepository.getEpisodesBySeasonCode(code).collect { res ->
                seasonReq.value = res
                seasonReq.value.data?.let { data ->
                    data.results?.let { res ->
                        seasons.value = res
                    }
                }
            }
        }
    }
    fun getLocationsByPageCode(page: Int){
        viewModelScope.launch(Dispatchers.IO) {
            locationReq.value = Resource.Loading(loading = true)
            locationsRepository.getLocationsByPageNumber(page).collect { res ->
                locationReq.value = res
                locationReq.value.data?.let { data ->
                    data.results?.let { res ->
                        locations.value = res
                    }
                }
            }
        }
    }
    private fun collectLikes(){
        viewModelScope.launch {
            rickAndMortyRepositoryDatabase.likes.collect { res ->
                likes.value = res
            }
        }
    }
    fun getData(){
        collectLikes()
        getLikes()
        getLocationsByPageCode(1)
        getSeasonsByFirstEpisodeCode("E01")
    }
    fun clear(){
        likes.value = emptyList()
        locationReq.value = Resource.Empty()
        seasonReq.value = Resource.Empty()
        locations.value = emptyList()
        seasons.value = emptyList()
    }

    companion object {
        const val TAG = "MFHomeViewModel"
    }
}