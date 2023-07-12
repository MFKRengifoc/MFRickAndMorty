package com.manoffocus.mfrickandmorty.screens.mflocation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manoffocus.mfrickandmorty.data.Resource
import com.manoffocus.mfrickandmorty.models.characters.MFCharacter
import com.manoffocus.mfrickandmorty.models.locations.MFLocation
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyCharactersRepository
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyLocationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MFLocationViewModel @Inject constructor(
    private val mfRickAndMortyLocationsRepository: MFRickAndMortyLocationsRepository,
    private val mfRickAndMortyCharactersRepository: MFRickAndMortyCharactersRepository
): ViewModel() {
    val locationReq : MutableState<Resource<MFLocation>> = mutableStateOf(Resource.Empty())
    val characters : MutableState<Resource<List<MFCharacter>>> = mutableStateOf(Resource.Empty())
    fun getLocationById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            locationReq.value = Resource.Loading(loading = true)
            mfRickAndMortyLocationsRepository.getLocationByIdCode(id).collect { resQ ->
                resQ.data?.apply {
                    locationReq.value = resQ
                    collectCharacters()
                }
            }
        }
    }

    private fun collectCharacters(){
        viewModelScope.launch(Dispatchers.IO) {
            locationReq.value.data?.let { dataLoc ->
                val resi = dataLoc.let { data ->
                    val dis = data.residents.map {  it.split("/").last().toInt() }.toTypedArray()
                    val pet = async {
                        mfRickAndMortyCharactersRepository.getCharactersByIdCodes(dis)
                    }
                    pet.await()
                }
                characters.value = resi.value
            }
        }
    }

    fun clear(){
        locationReq.value = Resource.Empty()
        characters.value = Resource.Empty()
    }
    companion object {
        const val TAG = "MFLocationViewModel"
    }
}