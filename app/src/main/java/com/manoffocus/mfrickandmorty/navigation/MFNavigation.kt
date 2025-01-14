package com.manoffocus.mfrickandmorty.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.manoffocus.mfrickandmorty.activities.mainactivity.MainViewModel
import com.manoffocus.mfrickandmorty.data.ResourceUser
import com.manoffocus.mfrickandmorty.screens.mfcharacter.MFCharacterScreen
import com.manoffocus.mfrickandmorty.screens.mfcharacter.MFCharacterViewModel
import com.manoffocus.mfrickandmorty.screens.mffaninfo.MFFanInfoScreen
import com.manoffocus.mfrickandmorty.screens.mfhome.MFHomeScreen
import com.manoffocus.mfrickandmorty.screens.mfhome.MFHomeViewModel
import com.manoffocus.mfrickandmorty.screens.mflocation.MFAllLocationsScreen
import com.manoffocus.mfrickandmorty.screens.mflocation.MFLocationScreen
import com.manoffocus.mfrickandmorty.screens.mflocation.MFLocationViewModel
import com.manoffocus.mfrickandmorty.screens.mfprofiler.MFProfilerScreen
import com.manoffocus.mfrickandmorty.screens.mfquiz.MFQuizScreen
import com.manoffocus.mfrickandmorty.screens.mfquiz.MFQuizViewModel
import com.manoffocus.mfrickandmorty.screens.mfsearch.MFSearchScreen
import com.manoffocus.mfrickandmorty.screens.mfsearch.MFSearchViewModel
import com.manoffocus.mfrickandmorty.screens.mfseason.MFSeasonScreen
import com.manoffocus.mfrickandmorty.screens.mfseason.MFSeasonViewModel
import com.manoffocus.mfrickandmorty.screens.mfsplash.MFSplashScreen
import com.manoffocus.mfrickandmorty.screens.mfuserprofile.MFUserProfileScreen
import com.manoffocus.mfrickandmorty.screens.mfuserprofile.MFUserProfileViewModel

@Composable
fun MFNavigation(
    navController: NavHostController,
    viewModel: MainViewModel,
    mfHomeViewModel: MFHomeViewModel,
    mfCharacterViewModel: MFCharacterViewModel,
    mfLocationViewModel: MFLocationViewModel,
    mfSeasonViewModel: MFSeasonViewModel,
    mfSearchViewModel: MFSearchViewModel,
    mfUserProfileViewModel: MFUserProfileViewModel,
    mfQuizViewModel: MFQuizViewModel,
    requestingBackScreen: MutableState<String>,
) {
    val TAG = "MFNavigation"
    val networkStatus = viewModel.networkStatus
    val user by viewModel.user
    val idLocation = rememberSaveable { mutableStateOf(-1) }
    val seasonCode = rememberSaveable { mutableStateOf("") }
    val characterId = rememberSaveable { mutableStateOf(-1) }
    val searchText = rememberSaveable { mutableStateOf("") }
    LaunchedEffect(key1 = idLocation.value){
        if (idLocation.value != -1){
            mfLocationViewModel.getLocationById(idLocation.value)
        }
    }
    LaunchedEffect(key1 = seasonCode.value){
        if (seasonCode.value != ""){
            mfSeasonViewModel.getEpisodesBySeasonCode(seasonCode.value)
        }
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
            ){
                requestingBackScreen.value = MFScreens.MFHomeScreen.name
            }
        }
        val mfLocationScreen = MFScreens.MFLocationScreen.name
        composable(route = "$mfLocationScreen/{idLocation}", arguments = listOf(navArgument(name = "idLocation"){
            type = NavType.IntType
        }) ){ entry ->
            entry.arguments?.getInt("idLocation")?.let { args ->
                idLocation.value = args
            }
            if (idLocation.value != -1){
                MFLocationScreen(
                    navController = navController,
                    connectedStatus = networkStatus,
                    mfLocationViewModel = mfLocationViewModel,
                    user = user.data
                ){
                    requestingBackScreen.value = MFScreens.MFLocationScreen.name
                    navController.popBackStack()
                }
            }
        }
        val mfAllLocationsScreen = MFScreens.MFAllLocationsScreen.name
        composable(route = mfAllLocationsScreen){
            MFAllLocationsScreen(
                navController = navController,
                connectedStatus = networkStatus,
                mfLocationViewModel = mfLocationViewModel,
                user = user.data
            ){
                requestingBackScreen.value = MFScreens.MFAllLocationsScreen.name
                navController.popBackStack()
            }
        }
        composable(MFScreens.MFUserProfileScreen.name){
            MFUserProfileScreen(
                navController = navController,
                mfUserProfileViewModel = mfUserProfileViewModel,
                user = user.data
            ){
                requestingBackScreen.value = MFScreens.MFUserProfileScreen.name
                navController.popBackStack()
            }
        }
        val mfSeasonScreen = MFScreens.MFSeasonScreen.name
        composable(route = "$mfSeasonScreen/{seasonCode}", arguments = listOf(navArgument(name = "seasonCode"){
            type = NavType.StringType
        })){ entry ->
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
                ){
                    requestingBackScreen.value = MFScreens.MFSeasonScreen.name
                    navController.popBackStack()
                }
            }
        }
        val mfCharacterScreen = MFScreens.MFCharacterScreen.name
        composable(route = "$mfCharacterScreen/{characterId}", arguments = listOf(navArgument(name = "characterId"){
            type = NavType.IntType
        })){ entry ->
            entry.arguments?.getInt("characterId")?.let { args ->
                characterId.value = args
            }
            if (characterId.value != -1){
                MFCharacterScreen (
                    navController = navController,
                    mfCharacterViewModel = mfCharacterViewModel,
                    user = user.data,
                    characterId = characterId
                )
            }
        }
        composable(MFScreens.MFSearchScreen.name){
            MFSearchScreen(
                navController = navController,
                mfSearchViewModel = mfSearchViewModel,
                connectedStatus = networkStatus,
                searchText = searchText
            ){
                requestingBackScreen.value = MFScreens.MFSearchScreen.name
                navController.popBackStack()
            }
        }
        composable(MFScreens.MFQuizScreen.name){
            MFQuizScreen (
                navController = navController,
                mfQuizViewModel = mfQuizViewModel,
                user = user.data
            ){
                requestingBackScreen.value = MFScreens.MFQuizScreen.name
                navController.popBackStack()
            }
        }
    }
}