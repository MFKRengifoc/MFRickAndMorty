package com.manoffocus.mfrickandmorty.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.manoffocus.mfrickandmorty.commons.Constants
import com.manoffocus.mfrickandmorty.data.RepositoryExceptionCodes
import com.manoffocus.mfrickandmorty.network.RickAndMortyAPI
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@HiltAndroidTest
class MFRickAndMortyLocationsRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)
    private val BASIC_TIME_SECS = 3L
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(BASIC_TIME_SECS, TimeUnit.SECONDS)
        .readTimeout(BASIC_TIME_SECS, TimeUnit.SECONDS)
        .build()

    @Before
    fun setup(){
        hiltAndroidRule.inject()
    }

    @Test
    fun getLocationsByPageCode_ReturnUnknownHostTest() {
        val rf = Retrofit.Builder()
            .baseUrl("http://unknown")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val api = rf.create(RickAndMortyAPI::class.java)
        val rickAndMortyLocationsRepository = MFRickAndMortyLocationsRepository(api)
        runBlocking {
            val req = rickAndMortyLocationsRepository.apiGetLocationsByPage(1)
            Assert.assertEquals(RepositoryExceptionCodes.UNKNOWN_HOST_EXCEPTION, req.code)
        }
    }
    @Test
    fun getLocationsByPageCode_ReturnSocketTimeOutTest() {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(BASIC_TIME_SECS, TimeUnit.SECONDS)
            .readTimeout(BASIC_TIME_SECS, TimeUnit.SECONDS)
            .build()
        val rf = Retrofit.Builder()
            .baseUrl("https://www.google.com:81")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val api = rf.create(RickAndMortyAPI::class.java)
        val rickAndMortyLocationsRepository = MFRickAndMortyLocationsRepository(api)
        runBlocking {
            val req = rickAndMortyLocationsRepository.apiGetLocationsByPage(1)
            Assert.assertEquals(RepositoryExceptionCodes.SOCKET_TIMEOUT_EXCEPTION, req.code)
        }
    }
    @Test
    fun getLocationsByPageCode_ReturnNotNullPageTest(){
        val rf = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val api = rf.create(RickAndMortyAPI::class.java)
        runBlocking {
            val page = 1
            val rickAndMortyLocationsRepository = MFRickAndMortyLocationsRepository(api)
            val req = rickAndMortyLocationsRepository.getLocationsByPageNumber(page)
            req.value.data?.let { data ->
                Assert.assertTrue(!data.results.isNullOrEmpty())
            }
        }
    }
    @Test
    fun getLocationsByPageCode_ReturnNotFoundPageTest(){
        val rf = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val api = rf.create(RickAndMortyAPI::class.java)
        runBlocking {
            val page = 20
            val rickAndMortyLocationsRepository = MFRickAndMortyLocationsRepository(api)
            val req = rickAndMortyLocationsRepository.getLocationsByPageNumber(page)
            Assert.assertEquals(RepositoryExceptionCodes.NOTHING_HERE_EXCEPTION, req.value.code)
        }
    }
    @Test
    fun getLocationByIdCode_ReturnLocationCodeTest() {
        val rf = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val api = rf.create(RickAndMortyAPI::class.java)
        runBlocking {
            val code = 1
            val rickAndMortyLocationsRepository = MFRickAndMortyLocationsRepository(api)
            val req = rickAndMortyLocationsRepository.getLocationByIdCode(code)
            req.value.data?.let { data ->
                Assert.assertEquals(code, data.id)
            }
        }
    }
    companion object {
        const val TAG = "MFLocationsRepositoryTest"
    }
}