package com.manoffocus.mfrickandmorty.models.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "character_like_tbl")
@TypeConverters(DateConverter::class)
data class CharacterLike(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "character_id")
    val characterId : Int,

    @ColumnInfo(name = "name")
    val name : String,
    @ColumnInfo(name = "character_image")
    val characterImage : String,
    @ColumnInfo(name = "user_id")
    val userId : Int,
    @ColumnInfo(name = "timestamp")
    val timestamp : Date
)