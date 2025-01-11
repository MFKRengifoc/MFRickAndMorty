package com.manoffocus.mfrickandmorty.repository

import android.util.Log
import com.manoffocus.mfrickandmorty.data.RepositoryExceptionCodes
import com.manoffocus.mfrickandmorty.data.Resource
import com.manoffocus.mfrickandmorty.models.episodes.EpisodesRequest
import com.manoffocus.mfrickandmorty.models.episodes.MFEpisode
import com.manoffocus.mfrickandmorty.network.RickAndMortyAPI
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

class MFRickAndMortyEpisodesRepository (private val api: RickAndMortyAPI) {
    /**
     * Returns a [EpisodesRequest] on a [Resource] object.
     * Must be used to connect with API.
     * @param code Is season code
     * */
    private suspend fun getEpisodesByCode(code: String): Resource<EpisodesRequest> {
        return try {
            val locationRequest = api.getEpisodesByCode(code)
            Resource.Success(data = locationRequest, code = RepositoryExceptionCodes.SUCCESS)
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
     * Returns a [MFEpisode] list  on a [Resource] object.
     * Must be used to connect with API.
     * @param ids Is a list of episodes ids to be formatted as string array literally
     * */
    suspend fun getEpisodesByIds(ids: Array<Int>): Resource<List<MFEpisode>> {
        return try {
            val idsStr = ids.contentToString()
            val locationRequest = api.getEpisodesByIds(idsStr)
            Resource.Success(data = locationRequest, code = RepositoryExceptionCodes.SUCCESS)
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
     * Returns a [EpisodesRequest] Flow on [Resource] object
     * Must be used to call an API method on [MFRickAndMortyCharactersRepository]
     * @param code Is season code
     * */
    suspend fun getEpisodesBySeasonCode(code: String): MutableStateFlow<Resource<EpisodesRequest>> {
        val episodesRequest = MutableStateFlow<Resource<EpisodesRequest>>(Resource.Empty())
        try {
            episodesRequest.emit(Resource.Loading())
            when(val response = getEpisodesByCode(code = code)){
                is Resource.Error -> {
                    response.message?.let {msg ->
                        episodesRequest.emit(Resource.Error(message = msg, code = response.code))
                    }
                }
                is Resource.Success -> {
                    episodesRequest.emit(response)
                } else ->{}
            }
        } catch (e: Exception){
            Log.d(TAG, "$e")
        }
        return episodesRequest
    }

    companion object {
        const val TAG = "MFEpisodesRepository"
    }
}