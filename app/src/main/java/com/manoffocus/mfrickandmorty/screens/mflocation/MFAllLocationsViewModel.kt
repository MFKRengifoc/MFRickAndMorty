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
    val locationReq : MutableState<Resource<LocationsRequest>> = mutableStateOf(Resource.Empty())
    var locations : MutableState<List<MFLocation>> = mutableStateOf(emptyList())

    suspend fun getLocationsByPageCode(page: Int){
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
}