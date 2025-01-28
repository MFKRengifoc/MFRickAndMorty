package com.manoffocus.mfrickandmorty.screens.mfsearch

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manoffocus.mfrickandmorty.data.Resource
import com.manoffocus.mfrickandmorty.models.characters.CharacterRequest
import com.manoffocus.mfrickandmorty.models.characters.MFCharacter
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyCharactersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.net.URI

class MFSearchViewModel(
    private val charactersRepository: MFRickAndMortyCharactersRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel(){
    val page = mutableStateOf(1)
    val activeNextPageButton = mutableStateOf(false)
    var searchItems : MutableState<List<MFCharacter>> = mutableStateOf(emptyList())
    val searchList : MutableState<Resource<CharacterRequest>> = mutableStateOf(Resource.Empty())
    val searchText = mutableStateOf("")
    val searchStatus = mutableStateOf("")
    val searchGender = mutableStateOf("")

    fun getCharactersBy(name: String, status: String, gender: String) {
        viewModelScope.launch(ioDispatcher) {

            searchText.value = name
            searchStatus.value = status
            searchGender.value = gender

            searchList.value = Resource.Loading()
            charactersRepository.getCharacterByFields(name = name, status = status, gender = gender).collect { res ->
                searchList.value = res
                searchItems.value = res.data?.results ?: emptyList()
                checkNextPageButton()
            }
        }
    }

    fun getCharactersByNextPage(page: Int, name: String, status: String, gender: String) {
        viewModelScope.launch(ioDispatcher) {
            searchList.value = Resource.Loading()
            charactersRepository.getCharacterByFieldsNextPage(page = page,name = name, status = status, gender = gender).collect { res ->
                searchList.value = res
                searchList.value.data?.let { data ->
                    data.results?.let { resList ->
                        if (searchItems.value.isEmpty()) {
                            searchItems.value = resList
                        } else {
                            searchItems.value += resList
                        }
                    }
                    checkNextPageButton()
                }
            }
        }
    }

    fun loadNextPage(){
        getCharactersByNextPage(
            page = page.value,
            name = searchText.value,
            status = searchStatus.value,
            gender = searchGender.value
        )
    }

    private fun checkNextPageButton(){
        searchList.value.data?.let { data ->
            data.info?.let { info ->
                page.value = if (info.next != null) getPageFromUrl(info.next)?: info.pages  else 1
                activeNextPageButton.value = info.next != null
            }
        }
    }

    private fun getPageFromUrl(url: String): Int? {
        val uri = URI(url)
        val query = uri.query
        val params = query.split("&")
        val pageParam = params.find { it.startsWith("page=") }
        return pageParam?.split("=")?.get(1)?.toIntOrNull()
    }

    fun clear(){
        searchList.value = Resource.Empty()
    }
    companion object {
        const val TAG = "MFSearchViewModel"
    }
}