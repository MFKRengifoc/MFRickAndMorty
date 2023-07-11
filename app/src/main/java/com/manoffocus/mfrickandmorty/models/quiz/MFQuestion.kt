package com.manoffocus.mfrickandmorty.models.quiz

data class MFQuestion(
    val correct: String,
    val descriptionCorrect: String,
    val image: String,
    val options: List<String>,
    val question: String
)