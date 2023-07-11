package com.manoffocus.mfrickandmorty.screens.mfuserprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.components.mfcharactersgrid.MFCharacterAvatar
import com.manoffocus.mfrickandmorty.components.mfcharactersgrid.MFCharacterAvatarSize
import com.manoffocus.mfrickandmorty.components.mflottie.MFIconLoader
import com.manoffocus.mfrickandmorty.components.mftabs.MFTabItem
import com.manoffocus.mfrickandmorty.components.mftabs.MFTabRow
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTexSizes
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTextTitle
import com.manoffocus.mfrickandmorty.components.mftopbar.MFTopBar
import com.manoffocus.mfrickandmorty.components.mfuserprofilecontent.MFLikesContent
import com.manoffocus.mfrickandmorty.components.mfuserprofilecontent.MFQuizzesContent
import com.manoffocus.mfrickandmorty.navigation.MFScreens
import com.manoffocus.mfrickandmorty.ui.theme.mfTextVPadding
import com.manoffocus.mfrickandmorty.ui.theme.topBottomPaddingBg
import com.manoffocus.mfrickandmorty.ui.theme.verticalPaddingBg

@Composable
fun MFUserProfileScreen(
   navController: NavController,
   mfUserProfileViewModel: MFUserProfileViewModel = hiltViewModel()
) {
    val user = mfUserProfileViewModel.user.value
    val finishedQuiz = mfUserProfileViewModel.finishedQuiz.collectAsState().value
    val likes = mfUserProfileViewModel.likes.value
    Scaffold(
        modifier = Modifier.background(MaterialTheme.colors.background),
        topBar = {
            MFTopBar(
                user = user,
                actualScreen = MFScreens.MFUserProfileScreen,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { it ->
        Surface(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(it)
                .padding(start = topBottomPaddingBg)
                .padding(vertical = verticalPaddingBg)
                .fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val parentModifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
            Column(
                modifier = parentModifier
                    .fillMaxHeight()
            ) {
                Row(
                    modifier = parentModifier
                        .fillMaxHeight(0.4F),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = parentModifier
                            .padding(bottom = topBottomPaddingBg),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        user?.let { us ->
                            MFCharacterAvatar(
                                characterUrl = us.avatarUrl,
                                characterName = us.name,
                                size = MFCharacterAvatarSize.LARGE
                            ){

                            }
                            MFText(
                                text = stringResource(id = R.string.mf_user_profile_screen_age_label, us.age),
                                size = MFTexSizes.SMALL
                            )
                        } ?: run {
                            MFIconLoader()
                        }
                    }
                }
                MFTabRow(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    tabItems = listOf(
                        MFTabItem(
                            label = stringResource(id = R.string.mf_user_profile_screen_quiz_label),
                            icon = Icons.Default.Star
                        ){
                            if (finishedQuiz.isNotEmpty()){
                                MFQuizzesContent(modifier = Modifier, quizzes = finishedQuiz)
                            } else {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    val msg = stringResource(id = R.string.mf_user_profile_screen_any_guard_label)
                                    val msgGuard = remember {
                                        mutableStateOf(msg)
                                    }
                                    MFTextTitle(
                                        modifier = Modifier.padding(vertical = mfTextVPadding * 2),
                                        text = stringResource(id = R.string.mf_user_profile_screen_any_expl_title_label),
                                        underLine = true
                                    )
                                    MFText(
                                        text = stringResource(id = R.string.mf_user_profile_screen_any_quiz_advice_one_label)
                                    )
                                }
                            }
                        },
                        MFTabItem(
                            label = stringResource(id = R.string.mf_user_profile_screen_likes_label),
                            icon = Icons.Default.Favorite
                        ){
                            if (likes.isNotEmpty()){
                                MFLikesContent(
                                    modifier = Modifier,
                                    likes = likes,
                                    onAvatarChosen = { id ->
                                        navController.navigate(MFScreens.MFCharacterScreen.name + "/${id}")
                                    },
                                ){ characterLiked ->
                                    mfUserProfileViewModel.deleteLike(
                                        characterId = characterLiked.characterId,
                                        characterImage = characterLiked.characterImage,
                                        name = characterLiked.name,
                                        userId = characterLiked.userId
                                    )
                                }
                            } else {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    val msg = stringResource(id = R.string.mf_user_profile_screen_any_guard_label)
                                    val msgGuard = remember {
                                        mutableStateOf(msg)
                                    }
                                    MFTextTitle(
                                        modifier = Modifier.padding(vertical = mfTextVPadding * 2),
                                        text = stringResource(id = R.string.mf_user_profile_screen_any_expl_title_label),
                                        underLine = true
                                    )
                                    MFText(
                                        text = stringResource(id = R.string.mf_user_profile_screen_any_like_advice_one_label)
                                    )
                                }
                            }
                        }
                    )
                )
            }
        }
    }
}
