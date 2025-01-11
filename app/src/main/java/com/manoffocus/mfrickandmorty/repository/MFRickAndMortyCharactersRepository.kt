package com.manoffocus.mfrickandmorty.repository

import android.util.Log
import com.manoffocus.mfrickandmorty.data.RepositoryExceptionCodes
import com.manoffocus.mfrickandmorty.data.Resource
import com.manoffocus.mfrickandmorty.models.characters.CharacterRequest
import com.manoffocus.mfrickandmorty.models.characters.MFCharacter
import com.manoffocus.mfrickandmorty.network.RickAndMortyAPI
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

class MFRickAndMortyCharactersRepository (private val api: RickAndMortyAPI) {
    /**
     * Returns a [MFCharacter] list  on a [Resource] object.
     * Must be used to connect with API.
     * @param ids Is a list of characters ids to be formatted as string array literally
     * */
    private suspend fun apiGetCharactersById(ids: Array<Int>): Resource<List<MFCharacter>> {
        return try {
            val idsStr = ids.contentToString()
            val characterRequest = api.getCharactersByIds(idsStr)
            Resource.Success(data = characterRequest, code = RepositoryExceptionCodes.SUCCESS)
        } catch (socketException: SocketTimeoutException){
            Resource.Error(message = socketException.message.toString(), code = RepositoryExceptionCodes.SOCKET_TIMEOUT_EXCEPTION)
        } catch (timeOutException: TimeoutException){
            Resource.Error(message = timeOutException.message.toString(), code = RepositoryExceptionCodes.TIMEOUT_EXCEPTION)
        } catch (unknownHostException: UnknownHostException){
            Resource.Error(message = unknownHostException.message.toString(), code = RepositoryExceptionCodes.UNKNOWN_HOST_EXCEPTION)
        } catch (httpException: HttpException){
            Resource.Error(
                message = httpException.message.toString(),
                code = if (httpException.code() == 404) RepositoryExceptionCodes.NOTHING_HERE_EXCEPTION else RepositoryExceptionCodes.NOT_FOUND
            )
        } catch (exception: Exception){
            Resource.Error(message = exception.message.toString(), code = RepositoryExceptionCodes.GENERAL_EXCEPTION)
        }
    }
    /**
     * Returns a [MFCharacter] on a [Resource] object.
     * Must be used to connect with API.
     * @param id Is a character id
     * */
    private suspend fun getCharacterById(id: Int): Resource<MFCharacter> {
        return try {
            val characterRequest = api.getCharacterById(id)
            Resource.Success(data = characterRequest, code = RepositoryExceptionCodes.SUCCESS)
        } catch (socketException: SocketTimeoutException){
            Resource.Error(message = socketException.message.toString(), code = RepositoryExceptionCodes.SOCKET_TIMEOUT_EXCEPTION)
        } catch (timeOutException: TimeoutException){
            Resource.Error(message = timeOutException.message.toString(), code = RepositoryExceptionCodes.TIMEOUT_EXCEPTION)
        } catch (unknownHostException: UnknownHostException){
            Resource.Error(message = unknownHostException.message.toString(), code = RepositoryExceptionCodes.UNKNOWN_HOST_EXCEPTION)
        } catch (httpException: HttpException){
            Resource.Error(
                message = httpException.message.toString(),
                code = if (httpException.code() == 404) RepositoryExceptionCodes.NOTHING_HERE_EXCEPTION else RepositoryExceptionCodes.NOT_FOUND
            )
        } catch (exception: Exception){
            Resource.Error(message = exception.message.toString(), code = RepositoryExceptionCodes.GENERAL_EXCEPTION)
        }
    }
    /**
     * Returns a [CharacterRequest] on a [Resource] object.
     * Must be used to connect with API.
     * @param name Is a name filter
     * @param status Is a status filter
     * @param gender Is a gender filter
     * */
    private suspend fun getCharacterBy(name: String, status: String, gender: String): Resource<CharacterRequest> {
        return try {
            val characterRequest = api.getCharacterBy(name = name, status = status, gender = gender)
            Resource.Success(data = characterRequest, code = RepositoryExceptionCodes.SUCCESS)
        } catch (socketException: SocketTimeoutException){
            Resource.Error(message = socketException.message.toString(), code = RepositoryExceptionCodes.SOCKET_TIMEOUT_EXCEPTION)
        } catch (timeOutException: TimeoutException){
            Resource.Error(message = timeOutException.message.toString(), code = RepositoryExceptionCodes.TIMEOUT_EXCEPTION)
        } catch (unknownHostException: UnknownHostException){
            Resource.Error(message = unknownHostException.message.toString(), code = RepositoryExceptionCodes.UNKNOWN_HOST_EXCEPTION)
        } catch (httpException: HttpException){
            Resource.Error(
                message = httpException.message.toString(),
                code = if (httpException.code() == 404) RepositoryExceptionCodes.NOTHING_HERE_EXCEPTION else RepositoryExceptionCodes.NOT_FOUND
            )
        } catch (exception: Exception){
            Resource.Error(message = exception.message.toString(), code = RepositoryExceptionCodes.GENERAL_EXCEPTION)
        }
    }
    /**
     * Returns a [CharacterRequest] Flow on [Resource] object
     * Must be used to call an API method on [MFRickAndMortyCharactersRepository]
     * @param name Is a name filter
     * @param status Is a status filter
     * @param gender Is a gender filter
     * */
    suspend fun getCharacterByFields(name: String, status: String, gender: String): MutableStateFlow<Resource<CharacterRequest>> {
        val characters = MutableStateFlow<Resource<CharacterRequest>>(Resource.Empty())
        try {
            characters.emit(Resource.Loading())
            when(val response = getCharacterBy(name = name, status = status, gender = gender)){
                is Resource.Error -> {
                    response.message?.let { msg ->
                        characters.emit(Resource.Error(message = msg, code = response.code))
                    }
                }
                is Resource.Success -> {
                    characters.emit(response)
                } else ->{}
            }
        } catch (e: Exception){
            Log.d(TAG, "$e")
        }
        return characters
    }
    /**
     * Returns a [MFCharacter] list Flow on [Resource] object
     * Must be used to call an API method on [MFRickAndMortyCharactersRepository]
     * @param ids Is a characters id list
     * */
    suspend fun getCharactersByIdCodes(ids: Array<Int>): MutableStateFlow<Resource<List<MFCharacter>>>{
        val characters = MutableStateFlow<Resource<List<MFCharacter>>>(Resource.Empty())
        try {
            when(val response = apiGetCharactersById(ids)){
                is Resource.Error -> {
                    response.message?.let { msg ->
                        characters.emit(Resource.Error(message = msg, code = response.code))
                    }
                }
                is Resource.Success -> {
                    characters.emit(response)
                } else ->{}
            }
        } catch (e: Exception){
            Log.d(TAG, "$e")
        }
        return characters
    }
    /**
     * Returns a [MFCharacter] Flow on [Resource] object
     * Must be used to call an API method on [MFRickAndMortyCharactersRepository]
     * @param id Is a characters id
     * */
    suspend fun getCharacterByIdCode(id: Int): MutableStateFlow<Resource<MFCharacter>> {
        val character = MutableStateFlow<Resource<MFCharacter>>(Resource.Empty())
        try {
            when(val response = getCharacterById(id)){
                is Resource.Error -> {
                    response.message?.let { msg ->
                        character.emit(Resource.Error(message = msg, code = response.code))
                    }
                }
                is Resource.Success -> {
                    character.emit(response)
                } else ->{}
            }
        } catch (e: Exception){
            Log.d(TAG, "$e")
        }
        return character
    }
    companion object {
        const val TAG = "MFCharactersRepository"
    }
}