package com.manoffocus.mfrickandmorty.screens.mfseason

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.components.mfcharactersgrid.MFCharacterAvatar
import com.manoffocus.mfrickandmorty.components.mfcharactersgrid.MFCharacterAvatarSize
import com.manoffocus.mfrickandmorty.components.mfcharactersguard.MFCharacterGuard
import com.manoffocus.mfrickandmorty.components.mfcharactersguard.MFCharacterMsgSize
import com.manoffocus.mfrickandmorty.components.mfcharactersguard.MFCharacterTextPosition
import com.manoffocus.mfrickandmorty.components.mfchipicon.MFChipInfoIcon
import com.manoffocus.mfrickandmorty.components.mfepisodes.MFEpisodeOverView
import com.manoffocus.mfrickandmorty.components.mfepisodes.MFEpisodes
import com.manoffocus.mfrickandmorty.components.mflottie.MFLoadingPlaceHolder
import com.manoffocus.mfrickandmorty.components.mflottie.MFLoadingPlaceHolderSize
import com.manoffocus.mfrickandmorty.components.mfscrollviews.MFHorizontal
import com.manoffocus.mfrickandmorty.components.mfsection.MFSectionForVertical
import com.manoffocus.mfrickandmorty.components.mfsnackbar.MFSnackbar
import com.manoffocus.mfrickandmorty.components.mfsurface.MFSurface
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTexSizes
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTextAppend
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTextAppends
import com.manoffocus.mfrickandmorty.components.mftopbar.MFTopBar
import com.manoffocus.mfrickandmorty.data.Resource
import com.manoffocus.mfrickandmorty.models.characters.MFCharacter
import com.manoffocus.mfrickandmorty.models.db.User
import com.manoffocus.mfrickandmorty.navigation.MFScreens
import com.manoffocus.mfrickandmorty.ui.theme.sidesPaddingBg
import com.manoffocus.mfrickandmorty.ui.theme.verticalPaddingBg

@Composable
fun MFSeasonScreen(
    navController: NavController,
    mfSeasonViewModel: MFSeasonViewModel,
    connectedStatus: MutableState<Pair<String, Boolean>>,
    seasonCode: MutableState<String>,
    user: User?
) {
    val episodesReq = mfSeasonViewModel.episodesReq.value
    LaunchedEffect(key1 = seasonCode.value){
        mfSeasonViewModel.getEpisodesBySeasonCode(seasonCode.value)
    }
    Scaffold(
        modifier = Modifier.background(MaterialTheme.colors.background),
        topBar = {
            MFTopBar(
                user = user,
                actualScreen = MFScreens.MFSeasonScreen,
                onBackClick = {
                    mfSeasonViewModel.clear()
                    navController.popBackStack()
                }
            )
        },
        snackbarHost = {
            MFSnackbar(
                msg = connectedStatus.value.first
            )
        }
    ) {
        MFSurface(
            modifier = Modifier
                .padding(it)
        )
        {
            val rowModifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .padding(vertical = verticalPaddingBg)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                if (connectedStatus.value.second){
                    MFSectionForVertical(
                        modifier = rowModifier
                            .padding(start = sidesPaddingBg)
                    ) {
                        val titleSeason = MFTextAppend(
                            text = stringResource(id = R.string.mf_season_screen_title_label),
                            color = MaterialTheme.colors.secondaryVariant,
                            weight = FontWeight.Bold,
                        )
                        val seasonNum = MFTextAppend(
                            text = seasonCode.value[2].toString()
                        )
                        MFTextAppends(
                            textArr = listOf(titleSeason, seasonNum),
                            fontSizes = MFTexSizes.LARGE,
                        )
                        episodesReq.data?.let { data ->
                            data.results?.let { episodes ->
                                MFText(
                                    text = stringResource(id = R.string.mf_season_screen_subtitle_label, episodes.size),
                                    size = MFTexSizes.SMALL
                                )
                            }
                        }
                    }
                    if (episodesReq is Resource.Loading || episodesReq is Resource.Empty){
                        MFLoadingPlaceHolder(
                            placeholder = R.raw.mf_placeholder_lottie,
                            size = MFLoadingPlaceHolderSize.LARGE
                        )
                        MFLoadingPlaceHolder(
                            placeholder = R.raw.mf_placeholder_lottie,
                            size = MFLoadingPlaceHolderSize.LARGE
                        )
                    } else {
                        if (episodesReq is Resource.Success){
                            episodesReq.data?.let { data ->
                                data.results?.let { episodes ->
                                    MFEpisodes(modifier = Modifier, list = episodes){ episode ->
                                        MFEpisodeOverView(
                                            episode = episode
                                        ){
                                            episode.charactersFull?.let { data ->
                                                MFHorizontal(
                                                    modifier = Modifier,
                                                    list = data
                                                ) { character ->
                                                    val character = character as MFCharacter
                                                    MFCharacterAvatar(
                                                        modifier = Modifier.padding(horizontal = sidesPaddingBg),
                                                        size = MFCharacterAvatarSize.SMALL,
                                                        characterUrl = character.image,
                                                        characterName = character.name
                                                    ) {
                                                        navController.navigate(MFScreens.MFCharacterScreen.name + "/${character.id}")
                                                    }
                                                }
                                            } ?: run {
                                                MFHorizontal(
                                                    modifier = Modifier,
                                                    list = episode.characters
                                                ) { character ->
                                                    Column(
                                                        modifier = Modifier
                                                            .size(MFCharacterAvatarSize.MEDIUM.size)
                                                            .padding(start = sidesPaddingBg),
                                                        verticalArrangement = Arrangement.Center,
                                                        horizontalAlignment = Alignment.CenterHorizontally
                                                    ) {
                                                        MFLoadingPlaceHolder(placeholder = R.raw.mf_placeholder_avatar_lottie, size = MFLoadingPlaceHolderSize.XSMALL)
                                                    }
                                                }
                                            }
                                        }
                                    }
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