package com.manoffocus.mfrickandmorty.activities.mainactivity

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.manoffocus.mfrickandmorty.navigation.MFNavigation
import com.manoffocus.mfrickandmorty.navigation.MFScreens
import com.manoffocus.mfrickandmorty.screens.mfcharacter.MFCharacterViewModel
import com.manoffocus.mfrickandmorty.screens.mfhome.MFHomeViewModel
import com.manoffocus.mfrickandmorty.screens.mflocation.MFLocationViewModel
import com.manoffocus.mfrickandmorty.screens.mfquiz.MFQuizViewModel
import com.manoffocus.mfrickandmorty.screens.mfsearch.MFSearchViewModel
import com.manoffocus.mfrickandmorty.screens.mfseason.MFSeasonViewModel
import com.manoffocus.mfrickandmorty.screens.mfuserprofile.MFUserProfileViewModel
import com.manoffocus.mfrickandmorty.ui.theme.MFRickAndMortyTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MFRickAndMortyApp()
        }
    }
    companion object {
        const val TAG = "MainActivity"
    }
}

@Composable
fun MFRickAndMortyApp(
    viewModel: MainViewModel = koinViewModel()
) {
    MFRickAndMortyTheme() {
        val navController = rememberNavController()
        val requestingBackScreen = rememberSaveable { mutableStateOf("")  }
        val ctx = LocalContext.current
        val mfHomeViewModel : MFHomeViewModel = koinViewModel()
        val mfCharacterViewModel: MFCharacterViewModel = koinViewModel()
        val mfLocationViewModel : MFLocationViewModel = koinViewModel()
        val mfSeasonViewModel: MFSeasonViewModel = koinViewModel()
        val mfSearchViewModel: MFSearchViewModel = koinViewModel()
        val mfUserProfileViewModel: MFUserProfileViewModel = koinViewModel()
        val mfQuizViewModel: MFQuizViewModel = koinViewModel()

        LaunchedEffect(Unit){
            viewModel.load()
            viewModel.getNetworkStatus(ctx)
            mfHomeViewModel.getData()
        }
        LaunchedEffect(requestingBackScreen.value){
            when(requestingBackScreen.value){
                MFScreens.MFHomeScreen.name -> {
                    val activity = ctx as Activity
                    activity.finish()
                    mfHomeViewModel.clear()
                }
                MFScreens.MFLocationScreen.name -> {
                    mfLocationViewModel.clear()
                }
                MFScreens.MFSeasonScreen.name -> {
                    mfSeasonViewModel.clear()
                }
                MFScreens.MFUserProfileScreen.name -> {
                    mfUserProfileViewModel.clear()
                }
                MFScreens.MFSearchScreen.name -> {
                    mfSearchViewModel.clear()
                }
            }
        }
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            color = MaterialTheme.colors.background
        ) {
            MFNavigation(
                navController = navController,
                viewModel = viewModel,
                mfHomeViewModel = mfHomeViewModel,
                mfCharacterViewModel = mfCharacterViewModel,
                mfLocationViewModel = mfLocationViewModel,
                mfSeasonViewModel = mfSeasonViewModel,
                mfSearchViewModel = mfSearchViewModel,
                mfUserProfileViewModel = mfUserProfileViewModel,
                mfQuizViewModel = mfQuizViewModel,
                requestingBackScreen = requestingBackScreen
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MFRickAndMortyApp()
}