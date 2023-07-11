package com.manoffocus.mfrickandmorty.models.characters

data class CharacterRequest(
    val info: Info? = null,
    var results: List<MFCharacter>?
)