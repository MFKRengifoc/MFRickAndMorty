package com.manoffocus.mfrickandmorty.screens.mfuserprofile

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manoffocus.mfrickandmorty.models.db.CharacterLike
import com.manoffocus.mfrickandmorty.models.db.Quiz
import com.manoffocus.mfrickandmorty.models.db.User
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyRepositoryDatabase
import com.manoffocus.mfrickandmorty.screens.mfhome.MFHomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MFUserProfileViewModel @Inject constructor(
    private val mfRickAndMortyRepositoryDatabase: MFRickAndMortyRepositoryDatabase
): ViewModel() {
    private val _user = MutableLiveData<User>()
    val user : LiveData<User> get() = _user
    private val _finishedQuiz = MutableStateFlow<List<Quiz>>(emptyList())
    val finishedQuiz = _finishedQuiz.asStateFlow()
    val likes : MutableState<List<CharacterLike>> = mutableStateOf(emptyList())
    init {
        getUsers()
        getFinishedQuiz()
        getLikes()
    }
    private fun getUsers(){
        viewModelScope.launch {
            mfRickAndMortyRepositoryDatabase.getUsersDB().distinctUntilChanged().collect { users ->
                if (users.isNotEmpty()){
                    _user.value = users[0]
                } else {
                    Log.d(MFHomeViewModel.TAG, "Nothing yet")
                }
            }
        }
    }
    private fun getFinishedQuiz(){
        viewModelScope.launch {
            mfRickAndMortyRepositoryDatabase.getAllQuiz().distinctUntilChanged().collect { finishedQuiz ->
                if (finishedQuiz.isNotEmpty()){
                    _finishedQuiz.value = finishedQuiz
                } else {
                    Log.d(MFHomeViewModel.TAG, "Nothing yet")
                }
            }
        }
    }
    private fun getLikes(){
        viewModelScope.launch(Dispatchers.IO) {
            mfRickAndMortyRepositoryDatabase.getAllLikes()
        }
    }
    fun deleteLike(characterId: Int, name: String, characterImage: String, userId: Int) {
        val like = CharacterLike(characterId = characterId, name = name, characterImage = characterImage, userId = userId, timestamp = Date())
        viewModelScope.launch { mfRickAndMortyRepositoryDatabase.deleteLike(like) }
    }
}