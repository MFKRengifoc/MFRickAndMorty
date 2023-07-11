package com.manoffocus.mfrickandmorty.screens.mfseason

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manoffocus.mfrickandmorty.data.Resource
import com.manoffocus.mfrickandmorty.models.episodes.EpisodesRequest
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyCharactersRepository
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyEpisodesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MFSeasonViewModel @Inject constructor(
    private val episodesRepository: MFRickAndMortyEpisodesRepository,
    private val charactersRepository: MFRickAndMortyCharactersRepository
): ViewModel() {
    val episodesReq : MutableState<Resource<EpisodesRequest>> = mutableStateOf(Resource.Empty())

    fun getEpisodesBySeasonCode(code: String){
        viewModelScope.launch(Dispatchers.IO) {
            if (episodesReq.value is Resource.Empty){
                episodesReq.value = Resource.Loading(loading = true)
                episodesRepository.getEpisodesBySeasonCode(code).collect { res ->
                    episodesReq.value = res
                    loadCharacters()
                }
            }
        }
    }
    private fun loadCharacters(){
        viewModelScope.launch(Dispatchers.IO) {
            episodesReq.value.data?.let { data ->
                data.results?.map { episode ->
                    val ids = episode.characters.map { it.split("/").last().toInt() }.toTypedArray()
                    val res = async(Dispatchers.IO) { charactersRepository.getCharactersByIdCodes(ids) }
                    episode.charactersFull = res.await().value.data
                }
            }
        }
    }
    fun clear(){
        episodesReq.value = Resource.Empty()
    }
    companion object {
        const val TAG = "MFSeasonViewModel"
    }
}