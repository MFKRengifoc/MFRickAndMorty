package com.manoffocus.mfrickandmorty.screens.mfquiz

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.manoffocus.mfrickandmorty.models.db.Quiz
import com.manoffocus.mfrickandmorty.models.quiz.MFQuiz
import com.manoffocus.mfrickandmorty.repository.MFRickAndMortyRepositoryDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MFQuizViewModel @Inject constructor(
    private val mfRickAndMortyRepositoryDatabase: MFRickAndMortyRepositoryDatabase
) : ViewModel() {
    val quiz : MutableState<MFQuiz> = mutableStateOf(MFQuiz())
    private val questionsFile = "questions.json"

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
    fun insertQuiz(totalQuestions: Int, passedQuestions: Int, timestampStarted: Date, userId: Long) {
        val quiz = Quiz(quizId = null, totalQuestions = totalQuestions, passedQuestions = passedQuestions, timestampStarted = timestampStarted, timestampFinished = Date(), userId = userId)
        viewModelScope.launch { mfRickAndMortyRepositoryDatabase.insertQuiz(quiz) }
    }
    companion object {
        const val TAG = "MFQuizViewModel"
    }
}