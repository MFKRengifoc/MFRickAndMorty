package com.manoffocus.mfrickandmorty.models.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "quiz_tbl")
@TypeConverters(DateConverter::class)
data class Quiz(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "quiz_id")
    val quizId: Int?,

    @ColumnInfo(name = "total_questions")
    val totalQuestions: Int,
    @ColumnInfo(name = "passed_questions")
    val passedQuestions: Int,
    @ColumnInfo(name = "timestamp_started")
    val timestampStarted: Date,
    @ColumnInfo(name = "timestamp_finished")
    val timestampFinished: Date,
    @ColumnInfo(name = "user_id")
    val userId: Int,
)