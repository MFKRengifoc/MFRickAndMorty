package com.manoffocus.mfrickandmorty.screens.mflocation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manoffocus.mfrickandmorty.data.Resource
import com.manoffocus.mfrickandmorty.models.characters.MFCharacter
import com.manoffocus.mfrickandmorty.models.db.CharacterLike
import com.manoffocus.mfrickandmorty.models.db.Location
import com.manoffocus.mfrickandmorty.models.locations.MFLocation
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyCharactersRepository
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyLocationsRepository
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyRepositoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class MFLocationViewModel(
    private val rickAndMortyRepositoryDatabase: MFRickAndMortyRepositoryDatabase,
    private val mfRickAndMortyLocationsRepository: MFRickAndMortyLocationsRepository,
    private val mfRickAndMortyCharactersRepository: MFRickAndMortyCharactersRepository
): ViewModel() {
    val locationReq : MutableState<Resource<MFLocation>> = mutableStateOf(Resource.Empty())
    val characters : MutableState<Resource<List<MFCharacter>>> = mutableStateOf(Resource.Empty())
    private val _locationLocal = MutableStateFlow<CharacterLike?>(null)
    val locationLocal = _locationLocal.asStateFlow()

    private fun visitLocationById(id: Int, name: String, type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            rickAndMortyRepositoryDatabase.getLocationById(id).distinctUntilChanged().collect { location ->
                if (location == null){
                    rickAndMortyRepositoryDatabase.insertLocation(Location(id, name, type))
                }
            }
        }
    }
    fun getLocationById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            locationReq.value = Resource.Loading()
            mfRickAndMortyLocationsRepository.getLocationByIdCode(id).collect { resQ ->
                resQ.data?.apply {
                    locationReq.value = resQ

                    val locationFound = resQ.data
                    locationFound?.let { location ->
                        visitLocationById(location.id, location.name, location.type)
                    }
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