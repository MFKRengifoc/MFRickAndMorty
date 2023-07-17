package com.manoffocus.mfrickandmorty.screens.mflocation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.components.mfcharactersgrid.MFCharacterGrid
import com.manoffocus.mfrickandmorty.components.mfcharactersguard.MFCharacterGuard
import com.manoffocus.mfrickandmorty.components.mfcharactersguard.MFCharacterMsgSize
import com.manoffocus.mfrickandmorty.components.mfcharactersguard.MFCharacterTextPosition
import com.manoffocus.mfrickandmorty.components.mfchipicon.MFChipInfoIcon
import com.manoffocus.mfrickandmorty.components.mflottie.MFLoadingPlaceHolder
import com.manoffocus.mfrickandmorty.components.mflottie.MFLoadingPlaceHolderSize
import com.manoffocus.mfrickandmorty.components.mfsection.MFSectionForVertical
import com.manoffocus.mfrickandmorty.components.mfsurface.MFSurface
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTexSizes
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTextTitle
import com.manoffocus.mfrickandmorty.components.mftopbar.MFTopBar
import com.manoffocus.mfrickandmorty.data.Resource
import com.manoffocus.mfrickandmorty.models.db.User
import com.manoffocus.mfrickandmorty.navigation.MFScreens
import com.manoffocus.mfrickandmorty.ui.theme.horizontalPaddingBg
import com.manoffocus.mfrickandmorty.ui.theme.verticalPaddingBg

@Composable
fun MFLocationScreen(
    navController: NavController,
    mfLocationViewModel: MFLocationViewModel,
    connectedStatus: MutableState<Pair<String, Boolean>>,
    user: User?,
    onBackClick: () -> Unit
) {
    BackHandler {
        onBackClick.invoke()
    }
    val locationReq = mfLocationViewModel.locationReq.value
    val characters = mfLocationViewModel.characters.value
    val rowModifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colors.background)
        .padding(vertical = verticalPaddingBg)
    Scaffold(
        modifier = Modifier.background(MaterialTheme.colors.background),
        topBar = {
            MFTopBar(
                user = user,
                actualScreen = MFScreens.MFLocationScreen,
                onBackClick = {
                    onBackClick.invoke()
                }
            )
        }
    ) { it ->
        MFSurface(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = horizontalPaddingBg)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (connectedStatus.value.second){
                    if (locationReq is Resource.Loading || locationReq is Resource.Empty){
                        MFSectionForVertical(modifier = rowModifier) {
                            MFLoadingPlaceHolder(
                                placeholder = R.raw.mf_loading_planets_lottie,
                                size = MFLoadingPlaceHolderSize.LARGE
                            )
                        }
                    } else {
                        if (locationReq is Resource.Success){
                            locationReq.data?.let { data ->
                                MFSectionForVertical(
                                    modifier = rowModifier,
                                    horizontalAlignmentC = Alignment.Start
                                ) {
                                    MFChipInfoIcon(
                                        icon = R.drawable.mf_location_icon,
                                        value = data.name,
                                        horizontal = true
                                    )
                                    MFChipInfoIcon(
                                        icon = R.drawable.mf_dimension_icon,
                                        value = data.dimension,
                                        horizontal = true
                                    )
                                    MFChipInfoIcon(
                                        icon = R.drawable.mf_info_icon,
                                        value = data.type,
                                        horizontal = true
                                    )
                                }
                                MFSectionForVertical(modifier = rowModifier) {
                                    Box(
                                        modifier = rowModifier,
                                        contentAlignment = Alignment.Center
                                    ){
                                        MFTextTitle(
                                            text = stringResource(
                                                id = R.string.mf_location_screen_residents_number_label, data.residents.size)
                                        )
                                    }
                                    Divider()
                                }
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
                    MFSectionForVertical(modifier = rowModifier) {
                        if (locationReq is Resource.Loading){
                                MFTextTitle(text = stringResource(id = R.string.mf_location_screen_loading_number_label))
                        } else {
                            if (locationReq is Resource.Success){
                                characters.data?.let { charsData ->
                                    MFCharacterGrid(
                                        modifier = Modifier,
                                        listOfCharacters = charsData,
                                        columns = 3
                                    ){ id, avatar ->
                                        navController.navigate(MFScreens.MFCharacterScreen.name + "/${id}")
                                    }
                                }
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