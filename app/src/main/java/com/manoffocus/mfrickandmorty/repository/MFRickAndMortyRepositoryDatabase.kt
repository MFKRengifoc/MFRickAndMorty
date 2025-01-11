package com.manoffocus.mfrickandmorty.repository

import com.manoffocus.mfrickandmorty.dao.MFRickAndMortyDao
import com.manoffocus.mfrickandmorty.data.ResourceUser
import com.manoffocus.mfrickandmorty.models.db.CharacterLike
import com.manoffocus.mfrickandmorty.models.db.Quiz
import com.manoffocus.mfrickandmorty.models.db.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class MFRickAndMortyRepositoryDatabase(private val rickAndMortyDao: MFRickAndMortyDao){
    private val _user = MutableStateFlow<ResourceUser<User>>(ResourceUser.Empty())
    val user : StateFlow<ResourceUser<User>> get() = _user.asStateFlow()

    private val _likes = MutableStateFlow<List<CharacterLike>>(emptyList())
    val likes : StateFlow<List<CharacterLike>> get() = _likes.asStateFlow()
    private val _quizzes = MutableStateFlow<List<Quiz>>(emptyList())
    val quizzes : StateFlow<List<Quiz>> get() = _quizzes.asStateFlow()


    suspend fun insertUser(user: User) : Long = rickAndMortyDao.insertUser(user)
    suspend fun deleteUser(user: User) = rickAndMortyDao.deleteUser(user)
    suspend fun deleteUsers() = rickAndMortyDao.deleteUsers()
    private fun getUsersDB(): Flow<List<User>> = rickAndMortyDao.getUsers()


    suspend fun getAllUsers(){
        getUsersDB().distinctUntilChanged().collect { users ->
            if (users.isNotEmpty()){
                _user.emit(ResourceUser.Success(data = users[0]))
            } else {
                _user.emit(ResourceUser.Success(data = null))
            }
        }
    }

    fun getLike(characterId: Int): Flow<CharacterLike> = rickAndMortyDao.getLike(characterId)
    private fun getLikes(): Flow<List<CharacterLike>> = rickAndMortyDao.getLikes()
    suspend fun getAllLikes(){
        getLikes().distinctUntilChanged().collect { likes ->
            _likes.value = likes
        }
    }

    suspend fun insertLike(like: CharacterLike) = rickAndMortyDao.insertLike(like)
    suspend fun deleteLike(like: CharacterLike) = rickAndMortyDao.deleteLike(like)
    suspend fun insertQuiz(quiz: Quiz) = rickAndMortyDao.insertQuiz(quiz)
    private fun getAllQuiz(): Flow<List<Quiz>> = rickAndMortyDao.getAllQuiz()
    suspend fun getAllQuizzes(){
        getAllQuiz().distinctUntilChanged().collect { quizzes ->
            _quizzes.value = quizzes
        }
    }
    companion object {
        const val TAG = "MFRepositoryDatabase"
    }
}