package com.manoffocus.mfrickandmorty.models.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "user_tbl")
@TypeConverters(DateConverter::class)
data class User(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val userId : Int?,

    @ColumnInfo(name = "name")
    val name : String,
    @ColumnInfo(name = "age")
    val age : Int,
    @ColumnInfo(name = "character_id")
    val characterId : Int,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl : String,
    @ColumnInfo(name = "timestamp")
    val timestamp : Date
)