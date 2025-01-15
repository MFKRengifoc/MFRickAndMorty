package com.manoffocus.mfrickandmorty.screens.mflocation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.components.mfcharactersguard.MFCharacterGuard
import com.manoffocus.mfrickandmorty.components.mfcharactersguard.MFCharacterMsgSize
import com.manoffocus.mfrickandmorty.components.mfcharactersguard.MFCharacterTextPosition
import com.manoffocus.mfrickandmorty.components.mfchipicon.MFChipInfoIcon
import com.manoffocus.mfrickandmorty.components.mflocations.MFLocations
import com.manoffocus.mfrickandmorty.components.mflottie.MFLoadingPlaceHolder
import com.manoffocus.mfrickandmorty.components.mflottie.MFLoadingPlaceHolderSize
import com.manoffocus.mfrickandmorty.components.mfsection.MFSectionForHorizontal
import com.manoffocus.mfrickandmorty.components.mfsection.MFSectionForVertical
import com.manoffocus.mfrickandmorty.components.mfsurface.MFSurface
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTexSizes
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.components.mftopbar.MFTopBar
import com.manoffocus.mfrickandmorty.data.Resource
import com.manoffocus.mfrickandmorty.models.db.User
import com.manoffocus.mfrickandmorty.navigation.MFScreens
import com.manoffocus.mfrickandmorty.ui.theme.horizontalPaddingBg
import com.manoffocus.mfrickandmorty.ui.theme.verticalPaddingBg

@Composable
fun MFAllLocationsScreen(
    navController: NavController,
    mfAllLocationsViewModel: MFAllLocationsViewModel,
    connectedStatus: MutableState<Pair<String, Boolean>>,
    user: User?,
    onBackClick: () -> Unit
){
    BackHandler {
        onBackClick.invoke()
        mfAllLocationsViewModel.clear()
    }
    val locationReq = mfAllLocationsViewModel.locationReq.value
    val locations = mfAllLocationsViewModel.locations.value
    val listState = rememberSaveable(saver = LazyListState.Saver) {
        LazyListState()
    }

    LaunchedEffect(mfAllLocationsViewModel.page){
        if (!mfAllLocationsViewModel.loadedInitialData.value){
            mfAllLocationsViewModel.getData()
        }
    }
    Scaffold(
        modifier = Modifier.background(MaterialTheme.colors.background),
        topBar = {
            MFTopBar(
                user = user,
                actualScreen = MFScreens.MFLocationScreen,
                onBackClick = {
                    onBackClick.invoke()
                    mfAllLocationsViewModel.clear()
                }
            )
        }
    ) { it ->
        MFSurface(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = horizontalPaddingBg)
        ) {
            val rowModifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .padding(vertical = verticalPaddingBg)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (connectedStatus.value.second){
                    MFSectionForVertical(
                        modifier = rowModifier,
                        horizontalAlignmentC = Alignment.Start
                    ) {
                        if (locationReq is Resource.Loading || locationReq is Resource.Empty){
                            MFLoadingPlaceHolder(
                                placeholder = R.raw.mf_loading_planets_lottie,
                                size = MFLoadingPlaceHolderSize.SMALL
                            )
                        } else {
                            if (locationReq is Resource.Success){
                                MFSectionForHorizontal(
                                    modifier = rowModifier
                                ) {
                                    val locationsLabel = stringResource(id = R.string.mf_all_locations_screen_total_label, locations.size)
                                    MFText(text = locationsLabel)
                                }
                                MFLocations(
                                    modifier = rowModifier,
                                    locationsList = locations,
                                    onLoadMoreLocations = { mfAllLocationsViewModel.nextPage() },
                                    state = listState,
                                    horizontal = false
                                ){ location ->
                                    navController.navigate(MFScreens.MFLocationScreen.name + "/${location.id}")
                                }
                            } else {
                                MFChipInfoIcon(
                                    modifier = Modifier.padding(vertical = verticalPaddingBg),
                                    fontSize = MFTexSizes.MEDIUM,
                                    icon = R.drawable.mf_alert_icon,
                                    value = stringResource(id = R.string.mf_error_loading_label),
                                    horizontal = true
                                )
                            }
                        }
                    }
                } else {
                    val msgGuard = stringResource(id = R.string.mf_home_screen_disconnected_label)
                    val mutableMsg = remember {
                        mutableStateOf(msgGuard)
                    }
                    MFCharacterGuard(
                        msg = mutableMsg,
                        dialogSize = MFCharacterMsgSize.BIG,
                        textPosition = MFCharacterTextPosition.LEFT
                    )
                }
            }
        }
    }
}