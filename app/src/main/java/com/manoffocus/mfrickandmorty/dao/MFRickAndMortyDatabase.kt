package com.manoffocus.mfrickandmorty.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.manoffocus.mfrickandmorty.models.db.CharacterLike
import com.manoffocus.mfrickandmorty.models.db.DateConverter
import com.manoffocus.mfrickandmorty.models.db.Quiz
import com.manoffocus.mfrickandmorty.models.db.User

@TypeConverters(DateConverter::class)
@Database(entities = [User::class, CharacterLike::class, Quiz::class], exportSchema = false, version = 1)
abstract class MFRickAndMortyDatabase: RoomDatabase() {
    abstract fun rickAndMortyDao(): MFRickAndMortyDao
}