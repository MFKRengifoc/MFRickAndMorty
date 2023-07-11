package com.manoffocus.mfrickandmorty.models.characters
import com.manoffocus.mfrickandmorty.models.episodes.MFEpisode

data class MFCharacter(
    val created: String,
    val episode: List<String>,
    var episodesFull: List<MFEpisode>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)