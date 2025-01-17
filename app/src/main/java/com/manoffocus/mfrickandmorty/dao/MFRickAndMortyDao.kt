package com.manoffocus.mfrickandmorty.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manoffocus.mfrickandmorty.models.db.CharacterLike
import com.manoffocus.mfrickandmorty.models.db.Location
import com.manoffocus.mfrickandmorty.models.db.Quiz
import com.manoffocus.mfrickandmorty.models.db.User
import kotlinx.coroutines.flow.Flow


@Dao
interface MFRickAndMortyDao {
    @Query("SELECT * FROM user_tbl")
    fun getUsers(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long
    @Delete
    suspend fun deleteUser(user: User)
    @Query("DELETE from user_tbl")
    suspend fun deleteUsers()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLike(like: CharacterLike)
    @Delete
    suspend fun deleteLike(like: CharacterLike)
    @Query("SELECT * FROM character_like_tbl where character_id=:characterId")
    fun getLike(characterId: Int): Flow<CharacterLike>
    @Query("SELECT * FROM character_like_tbl")
    fun getLikes(): Flow<List<CharacterLike>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuiz(quiz: Quiz)
    @Query("SELECT * FROM quiz_tbl")
    fun getAllQuiz(): Flow<List<Quiz>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: Location)
    @Query("SELECT * FROM location_tbl")
    fun getAllLocations(): Flow<List<Location>>
    @Query("SELECT * FROM location_tbl where location_id=:locationId")
    fun getLocationById(locationId: Int): Flow<Location>
}