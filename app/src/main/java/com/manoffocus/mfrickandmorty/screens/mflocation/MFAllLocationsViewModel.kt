package com.manoffocus.mfrickandmorty.screens.mflocation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manoffocus.mfrickandmorty.data.Resource
import com.manoffocus.mfrickandmorty.models.locations.LocationsRequest
import com.manoffocus.mfrickandmorty.models.locations.MFLocation
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyLocationsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class MFAllLocationsViewModel(
    private val locationsRepository: MFRickAndMortyLocationsRepository,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    var locations : MutableState<List<MFLocation>> = mutableStateOf(emptyList())
    val locationReq : MutableState<Resource<LocationsRequest>> = mutableStateOf(Resource.Empty())
    val page = mutableStateOf(1)
    val loadedInitialData = mutableStateOf(false)

    private suspend fun getLocationsByPageCode(page: Int){
        viewModelScope.launch(ioDispatcher) {
            locationReq.value = Resource.Loading()
            locationsRepository.getLocationsByPageNumber(page).collect { res ->
                locationReq.value = res
                locationReq.value.data?.let { data ->
                    data.results.let { res ->
                        if (locations.value.isEmpty()){
                            locations.value = res
                        } else {
                            locations.value += res
                        }
                    }
                }
            }
        }
    }

    suspend fun getData(){
        loadedInitialData.value = true
        getLocationsByPageCode(page.value)
    }

    fun nextPage() {
        page.value += 1
        viewModelScope.launch(ioDispatcher) {
            getLocationsByPageCode(page.value)
        }
    }

    fun clear(){
        locations.value = emptyList()
        page.value = 0
        loadedInitialData.value = false
    }
}