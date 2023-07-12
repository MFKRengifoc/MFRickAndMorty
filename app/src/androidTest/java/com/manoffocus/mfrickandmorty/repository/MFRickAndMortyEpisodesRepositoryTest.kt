package com.manoffocus.mfrickandmorty.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
import javax.inject.Inject

@HiltAndroidTest
class MFRickAndMortyEpisodesRepositoryTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)
    private val BASIC_TIME_SECS = 3L
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(BASIC_TIME_SECS, TimeUnit.SECONDS)
        .readTimeout(BASIC_TIME_SECS, TimeUnit.SECONDS)
        .build()
    @Inject
    lateinit var repository : MFRickAndMortyEpisodesRepository
    @Before
    fun setup(){
        hiltAndroidRule.inject()
    }
    @Test
    fun getEpisodesBySeasonCode_ReturnUnknownHost(){
        val rf = Retrofit.Builder()
            .baseUrl("http://unknown")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val api = rf.create(RickAndMortyAPI::class.java)
        val mfRickAndMortyEpisodesRepository = MFRickAndMortyEpisodesRepository(api)
        runBlocking {
            val code = "SO1"
            val req = mfRickAndMortyEpisodesRepository.getEpisodesBySeasonCode(code)
            Assert.assertEquals(RepositoryExceptionCodes.UNKNOWN_HOST_EXCEPTION, req.value.code)
        }
    }
    @Test
    fun getEpisodesBySeasonCode_ReturnSocketTimeOutTest(){
        val rf = Retrofit.Builder()
            .baseUrl("https://www.google.com:81")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val api = rf.create(RickAndMortyAPI::class.java)
        val mfRickAndMortyEpisodesRepository = MFRickAndMortyEpisodesRepository(api)
        runBlocking {
            val code = "SO1"
            val req = mfRickAndMortyEpisodesRepository.getEpisodesBySeasonCode(code)
            Assert.assertEquals(RepositoryExceptionCodes.SOCKET_TIMEOUT_EXCEPTION, req.value.code)
        }
    }
    @Test
    fun getEpisodesBySeasonCode_ReturnNotFoundTest(){
        runBlocking {
            val code = "S10"
            val req = repository.getEpisodesBySeasonCode(code)
            Assert.assertEquals(RepositoryExceptionCodes.NOTHING_HERE_EXCEPTION, req.value.code)
        }
    }
    @Test
    fun getEpisodesBySeasonCode_ReturnEpisodes(){
        runBlocking {
            val code = "S01"
            val req = repository.getEpisodesBySeasonCode(code)
            req.value.data?.let { data ->
                data.results?.let { results ->
                    var episodesOk = true
                    for (episode in results){
                        if (!episode.episode.contains(code)) episodesOk = false
                    }
                    Assert.assertTrue(episodesOk)
                }
            }
        }
    }
    @Test
    fun getEpisodesBySeasonCode_ReturnSeasonsByFirstEpisode(){
        runBlocking {
            val code = "E01"
            val req = repository.getEpisodesBySeasonCode(code)
            req.value.data?.let { data ->
                data.results?.let { results ->
                    var episodesOk = true
                    for (episode in results){
                        if (!episode.episode.contains(code)) episodesOk = false
                    }
                    Assert.assertTrue(episodesOk)
                }
            }
        }
    }
}