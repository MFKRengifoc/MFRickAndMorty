package com.manoffocus.mfrickandmorty.models.episodes

data class EpisodesRequest(
    val info: Info? = null,
    val results: List<MFEpisode>?
)