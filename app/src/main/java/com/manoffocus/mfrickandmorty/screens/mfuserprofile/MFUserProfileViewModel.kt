package com.manoffocus.mfrickandmorty.screens.mfuserprofile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manoffocus.mfrickandmorty.models.db.CharacterLike
import com.manoffocus.mfrickandmorty.models.db.Quiz
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyRepositoryDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MFUserProfileViewModel @Inject constructor(
    private val mfRickAndMortyRepositoryDatabase: MFRickAndMortyRepositoryDatabase
): ViewModel() {
    val likes : MutableState<List<CharacterLike>> = mutableStateOf(emptyList())
    val finishedQuiz : MutableState<List<Quiz>> = mutableStateOf(emptyList())
    private fun getLikes(){
        viewModelScope.launch(Dispatchers.IO) {
            mfRickAndMortyRepositoryDatabase.getAllLikes()
        }
    }
    private fun getQuizzes(){
        viewModelScope.launch(Dispatchers.IO) {
            mfRickAndMortyRepositoryDatabase.getAllQuizzes()
        }
    }
    private fun collectLikes(){
        viewModelScope.launch {
            mfRickAndMortyRepositoryDatabase.likes.collect { res ->
                likes.value = res
            }
        }
    }
    private fun collectQuizzes(){
        viewModelScope.launch {
            mfRickAndMortyRepositoryDatabase.quizzes.collect { res ->
                finishedQuiz.value = res
            }
        }
    }
    fun deleteLike(characterId: Int, name: String, characterImage: String, userId: Int) {
        val like = CharacterLike(characterId = characterId, name = name, characterImage = characterImage, userId = userId, timestamp = Date())
        viewModelScope.launch { mfRickAndMortyRepositoryDatabase.deleteLike(like) }
    }

    fun getData(){
        getLikes()
        getQuizzes()
        collectLikes()
        collectQuizzes()
    }
    fun clear(){
        finishedQuiz.value = emptyList()
        likes.value = emptyList()
    }
    companion object {
        const val TAG = "MFUserProfileViewModel"
    }
}