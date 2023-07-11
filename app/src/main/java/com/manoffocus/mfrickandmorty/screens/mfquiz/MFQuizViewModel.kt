package com.manoffocus.mfrickandmorty.screens.mfquiz

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.manoffocus.mfrickandmorty.models.db.Quiz
import com.manoffocus.mfrickandmorty.models.db.User
import com.manoffocus.mfrickandmorty.models.quiz.MFQuiz
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyRepositoryDatabase
import com.manoffocus.mfrickandmorty.screens.mfhome.MFHomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MFQuizViewModel @Inject constructor(
    private val mfRickAndMortyRepositoryDatabase: MFRickAndMortyRepositoryDatabase
) : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user : LiveData<User> get() = _user
    val quiz : MutableState<MFQuiz> = mutableStateOf(MFQuiz())
    private val questionsFile = "questions.json"
    init {
        getUsers()
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

    fun getJsonDataFromAsset(context: Context) {
        viewModelScope.launch {
            try {
                val quizString = context.assets.open(questionsFile).bufferedReader().use { it.readText() }
                val gson = Gson()
                val quizObj = object : TypeToken<MFQuiz>() {}.type
                val quizState: MFQuiz = gson.fromJson(quizString, quizObj)
                quiz.value = quizState
            } catch (ioException: IOException) {
                ioException.printStackTrace()
            }
        }
    }
    fun insertQuiz(totalQuestions: Int, passedQuestions: Int, timestampStarted: Date, userId: Int) {
        val quiz = Quiz(quizId = null, totalQuestions = totalQuestions, passedQuestions = passedQuestions, timestampStarted = timestampStarted, timestampFinished = Date(), userId = userId)
        viewModelScope.launch { mfRickAndMortyRepositoryDatabase.insertQuiz(quiz) }
    }
    companion object {
        const val TAG = "MFQuizViewModel"
    }
}