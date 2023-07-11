package com.manoffocus.mfrickandmorty.screens.mfcharacter

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manoffocus.mfrickandmorty.data.Resource
import com.manoffocus.mfrickandmorty.models.characters.MFCharacter
import com.manoffocus.mfrickandmorty.models.db.CharacterLike
import com.manoffocus.mfrickandmorty.models.episodes.MFEpisode
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyCharactersRepository
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyEpisodesRepository
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyRepositoryDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MFCharacterViewModel @Inject constructor(
    private val rickAndMortyRepositoryDatabase: MFRickAndMortyRepositoryDatabase,
    private val episodesRepository: MFRickAndMortyEpisodesRepository,
    private val charactersRepository: MFRickAndMortyCharactersRepository
) : ViewModel(){
    val character : MutableState<Resource<MFCharacter>> = mutableStateOf(Resource.Empty())
    val episodes : MutableState<List<MFEpisode>> = mutableStateOf(emptyList())
    private val _likedCharacter = MutableStateFlow<CharacterLike?>(null)
    val likedCharacter = _likedCharacter.asStateFlow()
    fun getLikedCharacter(characterId: Int) = viewModelScope.launch {
        rickAndMortyRepositoryDatabase.getLike(characterId = characterId).distinctUntilChanged().collect(){ liked ->
            _likedCharacter.value = liked
        }
    }

    private suspend fun getEpisodesByIds(
        ids: Array<Int>,
    ) : Resource<List<MFEpisode>> {
        return episodesRepository.getEpisodesByIds(ids)
    }
    fun getCharacterById(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            charactersRepository.getCharacterByIdCode(id).collect { res ->
                res.data?.let { data ->
                    character.value = res
                    val episodesId = data.episode.map { episode ->
                        episode.split("/").last().toInt()
                    }.toTypedArray()
                    val episodesAsync = async { getEpisodesByIds(episodesId) }
                    episodesAsync.await().data?.let { data ->
                        episodes.value = data
                    }
                }
            }
        }
    }



    fun insertLike(characterId: Int, name: String, characterImage: String, userId: Int) {
        val like = CharacterLike(characterId = characterId, name = name, characterImage = characterImage, userId = userId, timestamp = Date())
        viewModelScope.launch { rickAndMortyRepositoryDatabase.insertLike(like) }
    }
    fun deleteLike(characterId: Int, name: String, characterImage: String, userId: Int) {
        val like = CharacterLike(characterId = characterId, name = name, characterImage = characterImage, userId = userId, timestamp = Date())
        viewModelScope.launch { rickAndMortyRepositoryDatabase.deleteLike(like) }
    }

    fun clear(){
        character.value = Resource.Empty()
        _likedCharacter.value = null
    }

    companion object {
        const val TAG = "MFCharacterViewModel"
    }
}