package com.manoffocus.mfrickandmorty.screens.mflocation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import com.manoffocus.mfrickandmorty.models.db.User

@Composable
fun MFAllLocationsScreen(
    navController: NavController,
    mfLocationViewModel: MFLocationViewModel,
    connectedStatus: MutableState<Pair<String, Boolean>>,
    user: User?,
    onBackClick: () -> Unit
){
    BackHandler {
        onBackClick.invoke()
    }
}