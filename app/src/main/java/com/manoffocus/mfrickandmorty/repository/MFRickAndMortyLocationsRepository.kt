package com.manoffocus.mfrickandmorty.repository

import android.util.Log
import com.manoffocus.mfrickandmorty.data.RepositoryExceptionCodes
import com.manoffocus.mfrickandmorty.data.Resource
import com.manoffocus.mfrickandmorty.models.locations.LocationsRequest
import com.manoffocus.mfrickandmorty.models.locations.MFLocation
import com.manoffocus.mfrickandmorty.network.RickAndMortyAPI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class MFRickAndMortyLocationsRepository @Inject constructor(private val api: RickAndMortyAPI) {
    /**
     * Returns a [MFLocation] on a [Resource] object
     * Must be used to connect with API
     * @param id Is a [MFLocation] id
    * */
    private suspend fun apiGetLocationById(id: Int): Resource<MFLocation> {
        return try {
            val locationRequest = api.getLocationById(id)
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
     *  Returns a [LocationsRequest] on a [Resource] object
     *  Must be used to connect with API
     * @param page Is the number page
     * */
    suspend fun apiGetLocationsByPage(page: Int): Resource<LocationsRequest> {
        return try {
            val locationRequest = api.getLocationsByPage(page)
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
     *  It returns a [MFLocation] Flow on [Resource] object
     *  Must be used to call an API method on [MFRickAndMortyLocationsRepository]
     * @param id Is a [MFLocation] id
     * */
    suspend fun getLocationByIdCode(id: Int): StateFlow<Resource<MFLocation>>{
        val location = MutableStateFlow<Resource<MFLocation>>(Resource.Empty())
        try {
            location.emit(Resource.Loading())
            when(val response = apiGetLocationById(id)){
                is Resource.Error -> {
                    response.message?.let { msg ->
                        location.value = Resource.Error(message = msg, code = response.code)
                    }
                }
                is Resource.Success -> {
                    location.value = response
                } else ->{}
            }
        } catch (e: Exception){
            Log.d(TAG, "$e")
        }
        return location
    }

    /**
     *  It returns a [LocationsRequest] Flow on [Resource] object
     *  Must be used to call an API method on [MFRickAndMortyLocationsRepository]
     * @param page Is a number page
     * */
    suspend fun getLocationsByPageNumber(page: Int): MutableStateFlow<Resource<LocationsRequest>>{
        val locationReq = MutableStateFlow<Resource<LocationsRequest>>(Resource.Empty())
        try {
            locationReq.emit(Resource.Loading())
            when(val response = apiGetLocationsByPage(page)){
                is Resource.Error -> {
                    response.message?.let {msg ->
                        locationReq.emit(Resource.Error(message = msg, code = response.code))
                    }
                }
                is Resource.Success -> {
                    locationReq.emit(response)
                }
                else ->{}
            }
        } catch (e: Exception){
            Log.d(TAG, "$e")
        }
        return locationReq
    }
    companion object {
        const val TAG = "MFLocationsRepository"
    }
}