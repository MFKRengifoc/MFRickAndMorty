package com.manoffocus.mfrickandmorty.screens.mfsearch

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manoffocus.mfrickandmorty.data.Resource
import com.manoffocus.mfrickandmorty.models.characters.CharacterRequest
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyCharactersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MFSearchViewModel(
    private val charactersRepository: MFRickAndMortyCharactersRepository,
) : ViewModel(){
    val searchList : MutableState<Resource<CharacterRequest>> = mutableStateOf(Resource.Empty())
    fun getCharactersBy(name: String, status: String, gender: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchList.value = Resource.Loading()
            charactersRepository.getCharacterByFields(name = name, status = status, gender = gender).collect { res ->
                searchList.value = res
            }
        }
    }
    fun clear(){
        searchList.value = Resource.Empty()
    }
    companion object {
        const val TAG = "MFSearchViewModel"
    }
}