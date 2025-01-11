package com.manoffocus.mfrickandmorty.screens.mfprofiler

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manoffocus.mfrickandmorty.data.Resource
import com.manoffocus.mfrickandmorty.models.characters.MFCharacter
import com.manoffocus.mfrickandmorty.models.db.User
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyCharactersRepository
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyRepositoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class MFProfilerViewModel (
    private var charactersRepository: MFRickAndMortyCharactersRepository,
    private var rickAndMortyRepositoryDatabase: MFRickAndMortyRepositoryDatabase
    ): ViewModel() {
    var firstCharacters : MutableState<Resource<List<MFCharacter>>> = mutableStateOf(Resource.Empty())
    private val firstCharactersIds = arrayOf(1,2,3,4,5,7)
    private fun getCharacterById(ids: Array<Int>){
        viewModelScope.launch(Dispatchers.IO) {
            if (ids.isEmpty()){
                return@launch
            } else {
                charactersRepository.getCharactersByIdCodes(ids).collect { res ->
                    firstCharacters.value = res
                }
            }
        }
    }
    fun loadFirstCharacters(){
        getCharacterById(firstCharactersIds)
    }
    fun insertUser(name: String, age: Int, characterId: Int, avatarUrl: String, onComplete: (Boolean) -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            val user = User(userId = null,name = name, age = age, characterId = characterId, avatarUrl = avatarUrl, timestamp = Date())
            val insertId = rickAndMortyRepositoryDatabase.insertUser(user)
           onComplete.invoke((insertId != -1L))
        }
    }
    fun clear(){
        firstCharacters.value = Resource.Empty()
    }
    companion object {
        const val TAG = "MFProfilerViewModel"
    }
}