package com.manoffocus.mfrickandmorty.models.locations
import com.manoffocus.mfrickandmorty.models.characters.MFCharacter

data class MFLocation(
    val created: String,
    val dimension: String,
    val id: Int,
    val name: String,
    val residents: List<String>,
    var residentsFull: List<MFCharacter>? = null,
    val type: String,
    val url: String
)