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
class MFRickAndMortyCharactersRepositoryTest {
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
    fun getCharacterByFields_ReturnUnknownHostTest(){
        val rf = Retrofit.Builder()
            .baseUrl("http://unknown")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val api = rf.create(RickAndMortyAPI::class.java)
        val mfRickAndMortyCharactersRepository = MFRickAndMortyCharactersRepository(api)
        runBlocking {
            val name = ""
            val status = ""
            val gender = ""
            val req = mfRickAndMortyCharactersRepository.getCharacterByFields(
                name = name,
                status = status,
                gender = gender
            )
            Assert.assertEquals(RepositoryExceptionCodes.UNKNOWN_HOST_EXCEPTION, req.value.code)
        }
    }
    @Test
    fun getCharacterByFields_ReturnSocketTimeOutTest(){
        val rf = Retrofit.Builder()
            .baseUrl("https://www.google.com:81")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val api = rf.create(RickAndMortyAPI::class.java)
        val mfRickAndMortyCharactersRepository = MFRickAndMortyCharactersRepository(api)
        runBlocking {
            val name = ""
            val status = ""
            val gender = ""
            val req = mfRickAndMortyCharactersRepository.getCharacterByFields(
                name = name,
                status = status,
                gender = gender
            )
            Assert.assertEquals(RepositoryExceptionCodes.SOCKET_TIMEOUT_EXCEPTION, req.value.code)
        }
    }
    @Test
    fun getCharacterByFields_OnlyName_ReturnNotFoundTest(){
        val rf = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val api = rf.create(RickAndMortyAPI::class.java)
        val mfRickAndMortyCharactersRepository = MFRickAndMortyCharactersRepository(api)
        runBlocking {
            val name = "ricko"
            val status = ""
            val gender = ""
            val req = mfRickAndMortyCharactersRepository.getCharacterByFields(
                name = name,
                status = status,
                gender = gender
            )
            Assert.assertEquals(RepositoryExceptionCodes.NOTHING_HERE_EXCEPTION, req.value.code)
        }
    }
    @Test
    fun getCharacterByFields_OnlyStatus_ReturnNotFoundTest(){
        val rf = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val api = rf.create(RickAndMortyAPI::class.java)
        val mfRickAndMortyCharactersRepository = MFRickAndMortyCharactersRepository(api)
        runBlocking {
            val name = ""
            val status = "survived"
            val gender = ""
            val req = mfRickAndMortyCharactersRepository.getCharacterByFields(
                name = name,
                status = status,
                gender = gender
            )
            Assert.assertEquals(RepositoryExceptionCodes.NOTHING_HERE_EXCEPTION, req.value.code)
        }
    }
    @Test
    fun getCharacterByFields_OnlyGender_ReturnNotFoundTest(){
        val rf = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val api = rf.create(RickAndMortyAPI::class.java)
        val mfRickAndMortyCharactersRepository = MFRickAndMortyCharactersRepository(api)
        runBlocking {
            val name = ""
            val status = ""
            val gender = "helicopter"
            val req = mfRickAndMortyCharactersRepository.getCharacterByFields(
                name = name,
                status = status,
                gender = gender
            )
            Assert.assertEquals(RepositoryExceptionCodes.NOTHING_HERE_EXCEPTION, req.value.code)
        }
    }
    @Test
    fun getCharacterByFields_ReturnAllCharactersTest(){
        val rf = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val api = rf.create(RickAndMortyAPI::class.java)
        val mfRickAndMortyCharactersRepository = MFRickAndMortyCharactersRepository(api)
        runBlocking {
            val name = ""
            val status = ""
            val gender = ""
            val req = mfRickAndMortyCharactersRepository.getCharacterByFields(
                name = name,
                status = status,
                gender = gender
            )
            req.value.data?.let { data ->
                data.results?.let { results ->
                    Assert.assertTrue(results.isNotEmpty())
                }
            }
        }
    }
    @Test
    fun getCharactersByIdCodes_ReturnCharactersTest(){
        val rf = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val api = rf.create(RickAndMortyAPI::class.java)
        val mfRickAndMortyCharactersRepository = MFRickAndMortyCharactersRepository(api)
        runBlocking {
            val ids = arrayOf(1,2,3)
            val req = mfRickAndMortyCharactersRepository.getCharactersByIdCodes(ids)
            req.value.data?.let { data ->
                val charactersReq = data.filterIndexed { index, mfCharacter ->
                    mfCharacter.id == ids[index]
                }
                Assert.assertTrue(charactersReq.size == ids.size)
            }
        }
    }
    companion object {
        const val TAG = "MFCharactersRepositoryTest"
    }
}