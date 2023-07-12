package com.manoffocus.mfrickandmorty.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.manoffocus.mfrickandmorty.models.db.User
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date
import javax.inject.Inject

@HiltAndroidTest
class MFRickAndMortyRepositoryDatabaseTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)
    @Inject
    lateinit var database: MFRickAndMortyRepositoryDatabase
    lateinit var user : User
    @Before
    fun setup(){
        user = User(
            name = "Pepito",
            age = 21,
            characterId = 1,
            avatarUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            timestamp = Date(),
            userId = null
        )
        hiltAndroidRule.inject()
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insertUser_ReturnId() = runTest {
        val id = async { database.insertUser(user) }.await()
        if (id != -1L){
            user = user.copy(userId = id)
        }
    }

    @Test
    fun deleteUser_ReturnId() = runTest {
        launch { database.deleteUser(user) }
    }
    companion object {
        const val TAG = "MFRepositoryDatabaseTest"
    }
}