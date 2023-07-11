package com.manoffocus.mfrickandmorty.models.episodes

import com.manoffocus.mfrickandmorty.models.characters.MFCharacter
data class MFEpisode(
    val air_date: String,
    val characters: List<String>,
    var charactersFull: List<MFCharacter>?,
    val created: String,
    val episode: String,
    val id: Int,
    val name: String,
    val url: String
)