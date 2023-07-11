package com.manoffocus.mfrickandmorty.network

import com.manoffocus.mfrickandmorty.models.characters.CharacterRequest
import com.manoffocus.mfrickandmorty.models.characters.MFCharacter
import com.manoffocus.mfrickandmorty.models.episodes.EpisodesRequest
import com.manoffocus.mfrickandmorty.models.episodes.MFEpisode
import com.manoffocus.mfrickandmorty.models.locations.LocationsRequest
import com.manoffocus.mfrickandmorty.models.locations.MFLocation
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyAPI {
    @GET("character")
    suspend fun getAllCharacters(): CharacterRequest
    @GET("character/{ids}")
    suspend fun getCharactersByIds(@Path("ids") ids: String): List<MFCharacter>
    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): MFCharacter
    @GET("character")
    suspend fun getCharacterBy(@Query("name") name: String, @Query("status") status: String, @Query("gender") gender: String): CharacterRequest
    @GET("location/{id}")
    suspend fun getLocationById(@Path("id") id: Int): MFLocation
    @GET("location")
    suspend fun getLocationsByPage(@Query("page") page: Int): LocationsRequest
    @GET("episode/{ids}")
    suspend fun getEpisodesByIds(@Path("ids") ids: String): List<MFEpisode>
    @GET("episode")
    suspend fun getEpisodesByCode(@Query("episode") episode: String): EpisodesRequest
}