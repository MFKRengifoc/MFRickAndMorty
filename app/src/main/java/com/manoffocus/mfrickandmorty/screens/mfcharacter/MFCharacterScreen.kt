package com.manoffocus.mfrickandmorty.screens.mfcharacter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.components.mfcharactersgrid.MFCharacterAvatar
import com.manoffocus.mfrickandmorty.components.mfcharactersgrid.MFCharacterAvatarSize
import com.manoffocus.mfrickandmorty.components.mfchipicon.MFChipInfoIcon
import com.manoffocus.mfrickandmorty.components.mflottie.MFLoadingPlaceHolder
import com.manoffocus.mfrickandmorty.components.mflottie.MFLoadingPlaceHolderSize
import com.manoffocus.mfrickandmorty.components.mfscrollviews.MFVertical
import com.manoffocus.mfrickandmorty.components.mfsection.MFSectionForHorizontal
import com.manoffocus.mfrickandmorty.components.mfsection.MFSectionForVertical
import com.manoffocus.mfrickandmorty.components.mfsurface.MFSurface
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTexSizes
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTexTitleSizes
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTextTitle
import com.manoffocus.mfrickandmorty.components.mftopbar.MFTopBar
import com.manoffocus.mfrickandmorty.data.Resource
import com.manoffocus.mfrickandmorty.models.db.User
import com.manoffocus.mfrickandmorty.models.episodes.MFEpisode
import com.manoffocus.mfrickandmorty.navigation.MFScreens
import com.manoffocus.mfrickandmorty.ui.theme.horizontalPaddingBg
import com.manoffocus.mfrickandmorty.ui.theme.mfCharacterScreenCharTypeColor
import com.manoffocus.mfrickandmorty.ui.theme.topBottomPaddingBg
import com.manoffocus.mfrickandmorty.ui.theme.verticalPaddingBg

@Composable
fun MFCharacterScreen(
    navController: NavController,
    mfCharacterViewModel: MFCharacterViewModel,
    characterId: MutableState<Int>,
    user: User?
) {
    val likedCharacter by mfCharacterViewModel.likedCharacter.collectAsState()
    val character = mfCharacterViewModel.character.value
    val episodes = mfCharacterViewModel.episodes.value
    LaunchedEffect(key1 = characterId.value){
        mfCharacterViewModel.getCharacterById(characterId.value)
        mfCharacterViewModel.getLikedCharacter(characterId = characterId.value)
    }
    DisposableEffect(Unit){
        onDispose {
            mfCharacterViewModel.clear()
        }
    }
    Scaffold(
        modifier = Modifier.background(MaterialTheme.colors.background),
        topBar = {
            MFTopBar(
                user = user,
                actualScreen = MFScreens.MFCharacterScreen,
                likedCharacter = likedCharacter,
                onFavouriteClick = {
                    user?.let { us ->
                        character.let { char ->
                            char.data?.let { data ->
                                likedCharacter?.let { character ->
                                    mfCharacterViewModel.deleteLike(characterId.value, data.name, characterImage = character.characterImage, userId = data.id)
                                } ?: run {
                                    mfCharacterViewModel.insertLike(characterId.value, data.name, characterImage = data.image, userId = data.id)
                                }
                            }
                        }
                    }
                },
                onBackClick = {
                    mfCharacterViewModel.clear()
                    navController.popBackStack()
                }
            )
        }
    ) {
        MFSurface(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = horizontalPaddingBg)
        )
        {
            val parentModifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
            Column(
                modifier = parentModifier
                    .fillMaxHeight()
            ) {
                MFSectionForVertical(
                    modifier = parentModifier,
                    horizontalAlignmentC = Alignment.CenterHorizontally
                ) {
                    if (character is Resource.Loading || character is Resource.Empty){
                        MFLoadingPlaceHolder(
                            placeholder = R.raw.mf_loading_planets_lottie,
                            size = MFLoadingPlaceHolderSize.MEDIUM
                        )
                    } else {
                        if (character is Resource.Success){
                            character.data?.let { char ->
                                MFCharacterAvatar(
                                    characterUrl = char.image,
                                    characterName = char.name,
                                    size = MFCharacterAvatarSize.LARGE
                                ){}
                                MFText(
                                    text = char.type,
                                    color = mfCharacterScreenCharTypeColor,
                                    size = MFTexSizes.SMALL
                                )
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
                MFSectionForHorizontal(
                    modifier = parentModifier
                        .fillMaxHeight(0.25F)
                        .padding(top = topBottomPaddingBg),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    if (character is Resource.Loading){

                    } else {
                        if (character is Resource.Success){
                            character.data?.let { char ->
                                MFChipInfoIcon(
                                    modifier = Modifier,
                                    icon = R.drawable.mf_status_icon,
                                    value = char.status
                                )
                                MFChipInfoIcon(
                                    modifier = Modifier,
                                    icon = R.drawable.mf_location_icon,
                                    value = char.location.name
                                )
                                MFChipInfoIcon(
                                    modifier = Modifier,
                                    icon = R.drawable.mf_gender_icon,
                                    value = char.gender
                                )
                            }
                        }
                    }
                }
                MFSectionForVertical(
                    modifier = parentModifier
                        .fillMaxHeight()
                        .padding(top = topBottomPaddingBg),
                ) {
                    MFTextTitle(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.mf_season_screen_appear_label),
                        align = TextAlign.Start
                    )
                    Divider()
                    character?.let { char ->
                        val verticalModifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                        MFVertical(
                            modifier = verticalModifier,
                            list = episodes) { epi ->
                            val MFEpisode = epi as MFEpisode
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .padding(vertical = verticalPaddingBg),
                                elevation = 0.dp,
                                backgroundColor = Color.Transparent
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = horizontalPaddingBg)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth(0.8F)
                                            .fillMaxHeight(),
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        MFTextTitle(
                                            text = MFEpisode.name,
                                            size = MFTexTitleSizes.SMALL,
                                            underLine = true
                                        )
                                        MFText(
                                            text = MFEpisode.air_date,
                                            color = MaterialTheme.colors.secondary,
                                            size = MFTexSizes.SMALL
                                        )
                                    }
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight(),
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        MFText(
                                            text = MFEpisode.episode,
                                            size = MFTexSizes.SMALL
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}