package com.manoffocus.mfrickandmorty.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.manoffocus.mfrickandmorty.activities.mainactivity.MainViewModel
import com.manoffocus.mfrickandmorty.data.ResourceUser
import com.manoffocus.mfrickandmorty.screens.mfcharacter.MFCharacterScreen
import com.manoffocus.mfrickandmorty.screens.mffaninfo.MFFanInfoScreen
import com.manoffocus.mfrickandmorty.screens.mfhome.MFHomeScreen
import com.manoffocus.mfrickandmorty.screens.mfhome.MFHomeViewModel
import com.manoffocus.mfrickandmorty.screens.mflocation.MFLocationScreen
import com.manoffocus.mfrickandmorty.screens.mflocation.MFLocationViewModel
import com.manoffocus.mfrickandmorty.screens.mfprofiler.MFProfilerScreen
import com.manoffocus.mfrickandmorty.screens.mfquiz.MFQuizScreen
import com.manoffocus.mfrickandmorty.screens.mfsearch.MFSearchScreen
import com.manoffocus.mfrickandmorty.screens.mfsearch.MFSearchViewModel
import com.manoffocus.mfrickandmorty.screens.mfseason.MFSeasonScreen
import com.manoffocus.mfrickandmorty.screens.mfseason.MFSeasonViewModel
import com.manoffocus.mfrickandmorty.screens.mfsplash.MFSplashScreen
import com.manoffocus.mfrickandmorty.screens.mfuserprofile.MFUserProfileScreen

@Composable
fun MFNavigation(
    viewModel: MainViewModel,
    networkStatus: MutableState<Pair<String, Boolean>>,
    navController: NavHostController,
) {
    val user by viewModel.user
    val mfHomeViewModel : MFHomeViewModel = hiltViewModel()
    val mfLocationViewModel : MFLocationViewModel = hiltViewModel()
    val mfSeasonViewModel: MFSeasonViewModel = hiltViewModel()
    val mfSearchViewModel: MFSearchViewModel = hiltViewModel()
    LaunchedEffect(networkStatus.value){
        mfHomeViewModel.getData()
    }
    NavHost(navController = navController, startDestination = MFScreens.MFSplashScreen.name){
        composable(MFScreens.MFSplashScreen.name){
            if (user is ResourceUser.Success){
                MFSplashScreen(
                    navController = navController,
                    user = user.data
                )
            }
        }
        composable(MFScreens.MFProfilerScreen.name){
            MFProfilerScreen(
                navController = navController,
                networkStatus = networkStatus
            )
        }
        composable(MFScreens.MFFanInfoScreen.name){
            MFFanInfoScreen(
                navController = navController
            )
        }
        composable(MFScreens.MFHomeScreen.name){
            MFHomeScreen(
                navController = navController,
                mfHomeViewModel = mfHomeViewModel,
                connectedStatus = networkStatus,
                user = user.data
            )
        }
        val mfLocationScreen = MFScreens.MFLocationScreen.name
        composable(route = "$mfLocationScreen/{idLocation}", arguments = listOf(navArgument(name = "idLocation"){
            type = NavType.IntType
        }) ){ entry ->
            val id = rememberSaveable { mutableStateOf(-1) }
            entry.arguments?.getInt("idLocation")?.let { args ->
                id.value = args
            }
            LaunchedEffect(key1 = id.value){
                mfLocationViewModel.getLocationById(id.value)
            }
            if (id.value != -1){
                MFLocationScreen(
                    navController = navController,
                    connectedStatus = networkStatus,
                    mfLocationViewModel = mfLocationViewModel,
                    locationId = id,
                    user = user.data
                )
            }
        }
        composable(MFScreens.MFUserProfileScreen.name){
            MFUserProfileScreen(
                navController = navController,
                user = user.data
            )
        }
        val mfSeasonScreen = MFScreens.MFSeasonScreen.name
        composable(route = "$mfSeasonScreen/{seasonCode}", arguments = listOf(navArgument(name = "seasonCode"){
            type = NavType.StringType
        })){ entry ->
            val seasonCode = remember { mutableStateOf("") }
            entry.arguments?.getString("seasonCode")?.let { args ->
                seasonCode.value = args
            }
            if (seasonCode.value != ""){
                MFSeasonScreen(
                    navController = navController,
                    mfSeasonViewModel = mfSeasonViewModel,
                    connectedStatus = networkStatus,
                    seasonCode = seasonCode,
                    user = user.data
                )
            }
        }
        val mfCharacterScreen = MFScreens.MFCharacterScreen.name
        composable(route = "$mfCharacterScreen/{characterId}", arguments = listOf(navArgument(name = "characterId"){
            type = NavType.IntType
        })){ entry ->
            val characterId = remember { mutableStateOf(-1) }
            entry.arguments?.getInt("characterId")?.let { args ->
                characterId.value = args
            }
            if (characterId.value != -1){
                MFCharacterScreen(
                    navController = navController,
                    user = user.data,
                    characterId = characterId
                )
            }
        }
        composable(MFScreens.MFSearchScreen.name){
            MFSearchScreen(
                navController = navController,
                mfSearchViewModel = mfSearchViewModel,
                connectedStatus = networkStatus
            )
        }
        composable(MFScreens.MFQuizScreen.name){
            MFQuizScreen(
                navController = navController,
                user = user.data
            )
        }
    }
}