package com.manoffocus.mfrickandmorty.screens.mfhome

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.components.mfcharactersgrid.MFCharacterAvatar
import com.manoffocus.mfrickandmorty.components.mfcharactersgrid.MFCharacterAvatarSize
import com.manoffocus.mfrickandmorty.components.mfcharactersguard.MFCharacterGuard
import com.manoffocus.mfrickandmorty.components.mfcharactersguard.MFCharacterMsgSize
import com.manoffocus.mfrickandmorty.components.mfcharactersguard.MFCharacterTextPosition
import com.manoffocus.mfrickandmorty.components.mfchipicon.MFChipInfoIcon
import com.manoffocus.mfrickandmorty.components.mflocations.MFLocationSize
import com.manoffocus.mfrickandmorty.components.mflocations.MFLocations
import com.manoffocus.mfrickandmorty.components.mflocations.MFVisitedLocation
import com.manoffocus.mfrickandmorty.components.mflottie.MFLoadingPlaceHolder
import com.manoffocus.mfrickandmorty.components.mflottie.MFLoadingPlaceHolderSize
import com.manoffocus.mfrickandmorty.components.mfscrollviews.MFHorizontal
import com.manoffocus.mfrickandmorty.components.mfseasons.MFSeasons
import com.manoffocus.mfrickandmorty.components.mfsection.MFSectionForVertical
import com.manoffocus.mfrickandmorty.components.mfsnackbar.MFSnackbar
import com.manoffocus.mfrickandmorty.components.mfsurface.MFSurface
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFComplexHeader
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTexSizes
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTextTitle
import com.manoffocus.mfrickandmorty.components.mftopbar.MFTopBar
import com.manoffocus.mfrickandmorty.data.Resource
import com.manoffocus.mfrickandmorty.models.db.CharacterLike
import com.manoffocus.mfrickandmorty.models.db.Location
import com.manoffocus.mfrickandmorty.models.db.User
import com.manoffocus.mfrickandmorty.models.seasons.Season
import com.manoffocus.mfrickandmorty.navigation.MFScreens
import com.manoffocus.mfrickandmorty.ui.theme.sidesPaddingBg
import com.manoffocus.mfrickandmorty.ui.theme.verticalPaddingBg

@Composable
fun MFHomeScreen(
    navController: NavController,
    mfHomeViewModel: MFHomeViewModel,
    connectedStatus: MutableState<Pair<String, Boolean>>,
    user: User?,
    onBackClick: () -> Unit
) {
    val likes by mfHomeViewModel.likes
    val locationsVisited by mfHomeViewModel.locationsVisited
    val locationReq = mfHomeViewModel.locationReq.value
    val seasonReq = mfHomeViewModel.seasonReq.value
    val locations = mfHomeViewModel.locations.value
    val seasons by mfHomeViewModel.seasons
    BackHandler {
        onBackClick.invoke()
    }
    Scaffold(
        modifier = Modifier.background(MaterialTheme.colors.background),
        topBar = {
            MFTopBar(
                user = user,
                onSearchButtonClick = {
                    navController.navigate(MFScreens.MFSearchScreen.name)
                },
                onUserProfileClick = {
                    navController.navigate(MFScreens.MFUserProfileScreen.name)
                }
            )
        },
        snackbarHost = {
            MFSnackbar(
                msg = connectedStatus.value.first
            )
        },
        floatingActionButton = {
            val label = stringResource(id = R.string.mf_home_screen_test_question_jerry_label)
            val msg = remember {
                mutableStateOf(label)
            }
            MFCharacterGuard(
                icon = R.drawable.mf_jerry_icon,
                dialogSize = MFCharacterMsgSize.SMALL,
                textPosition = MFCharacterTextPosition.LEFT,
                backgroundColor = Color.White,
                messageIcon = R.drawable.mf_dialog_icon_white,
                textColor = Color.Black,
                msg = msg
            ){
                navController.navigate(MFScreens.MFQuizScreen.name)
            }
        }
    ) { it ->
        MFSurface(
            modifier = Modifier
                .padding(it)
                .padding(start = sidesPaddingBg)
        ) {
            val rowModifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .padding(vertical = verticalPaddingBg)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (connectedStatus.value.second){
                    MFSectionForVertical(
                        modifier = rowModifier,
                        horizontalAlignmentC = Alignment.Start
                    ) {
                        MFComplexHeader(
                            modifier = Modifier,
                            title = stringResource(id = R.string.mf_home_screen_title_locations_label),
                            actionText = stringResource(id = R.string.mf_home_screen_see_more_data_label)
                        ){
                            navController.navigate(MFScreens.MFAllLocationsScreen.name)
                        }
                        if (locationReq is Resource.Loading || locationReq is Resource.Empty){
                            MFLoadingPlaceHolder(
                                placeholder = R.raw.mf_loading_planets_lottie,
                                size = MFLoadingPlaceHolderSize.SMALL
                            )
                        } else {
                            if (locationReq is Resource.Success){
                                MFLocations(
                                    modifier = rowModifier,
                                    locationsList = locations
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
                    MFSectionForVertical(
                        modifier = rowModifier,
                        horizontalAlignmentC = Alignment.Start
                    ) {
                        MFTextTitle(
                            modifier = Modifier.padding(start = sidesPaddingBg),
                            text = stringResource(id = R.string.mf_home_screen_title_seasons_label)
                        )
                        if (seasonReq is Resource.Loading || locationReq is Resource.Empty){
                            MFLoadingPlaceHolder(
                                placeholder = R.raw.mf_placeholder_lottie,
                                speed = 0.8F,
                                size = MFLoadingPlaceHolderSize.MEDIUM
                            )
                        } else {
                            if (seasonReq is Resource.Success){
                                val seasons = seasons.map { season ->
                                    Season(firstEpisodeName = season.name, firstEpisodeDate = season.air_date, firsEpisodeCode = season.episode)
                                }
                                MFSeasons(
                                    modifier = Modifier,
                                    seasons = seasons
                                ){ season ->
                                    navController.navigate(MFScreens.MFSeasonScreen.name + "/${season.firsEpisodeCode.subSequence(0,3)}")
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
                    if (likes.isNotEmpty()){
                        MFSectionForVertical(
                            modifier = rowModifier,
                            horizontalAlignmentC = Alignment.Start
                        ) {
                            MFTextTitle(
                                modifier = Modifier.padding(start = sidesPaddingBg),
                                text = stringResource(id = R.string.mf_home_screen_likes_label),
                                maxWidth = 300.dp,
                                align = TextAlign.Start
                            )
                            if (likes.isNotEmpty()){
                                MFHorizontal(
                                    modifier = Modifier,
                                    list = likes
                                ) { like ->
                                    val characterLike = like as CharacterLike
                                    Column(
                                        modifier = Modifier
                                            .size(MFCharacterAvatarSize.MEDIUM.size)
                                            .padding(start = sidesPaddingBg),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        MFCharacterAvatar(
                                            modifier = Modifier,
                                            size = MFCharacterAvatarSize.SMALL,
                                            characterUrl = characterLike.characterImage,
                                            characterName = characterLike.name,
                                        ){
                                            navController.navigate(MFScreens.MFCharacterScreen.name + "/${characterLike.characterId}")
                                        }
                                    }
                                }
                            } else {
                                MFText(
                                    modifier = Modifier.padding(start = sidesPaddingBg * 2),
                                    text = stringResource(id = R.string.mf_home_screen_not_likes_label),
                                    align = TextAlign.Center
                                )
                            }
                        }
                    }

                    if (locationsVisited.isNotEmpty()){
                        MFSectionForVertical(
                            modifier = rowModifier,
                            horizontalAlignmentC = Alignment.Start
                        ) {
                            MFTextTitle(
                                modifier = Modifier.padding(start = sidesPaddingBg),
                                text = stringResource(id = R.string.mf_home_screen_locations_label),
                                maxWidth = 300.dp,
                                align = TextAlign.Start
                            )
                            if (locationsVisited.isNotEmpty()){
                                MFHorizontal(
                                    modifier = Modifier,
                                    list = locationsVisited
                                ) { location ->
                                    val locationVisited = location as Location
                                    Column(
                                        modifier = Modifier
                                            .size(MFCharacterAvatarSize.MEDIUM.size)
                                            .padding(start = sidesPaddingBg),
                                        verticalArrangement = Arrangement.Center
                                    ){
                                        MFVisitedLocation(
                                            modifier = Modifier,
                                            location = locationVisited,
                                            size = MFLocationSize.XSMALL
                                        ) {
                                            navController.navigate(MFScreens.MFLocationScreen.name + "/${locationVisited.locationId}")
                                        }
                                    }
                                }
                            } else {
                                MFText(
                                    modifier = Modifier.padding(start = sidesPaddingBg * 2),
                                    text = stringResource(id = R.string.mf_home_screen_not_likes_label),
                                    align = TextAlign.Center
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


