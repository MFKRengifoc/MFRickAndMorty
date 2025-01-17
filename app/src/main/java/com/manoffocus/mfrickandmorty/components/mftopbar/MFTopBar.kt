package com.manoffocus.mfrickandmorty.components.mftopbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.components.mfcharactersgrid.MFCharacterAvatar
import com.manoffocus.mfrickandmorty.components.mfcharactersgrid.MFCharacterAvatarSize
import com.manoffocus.mfrickandmorty.components.mfform.MFUserForm
import com.manoffocus.mfrickandmorty.components.mfform.MFUserFormInputData
import com.manoffocus.mfrickandmorty.components.mfform.MFUserFormInputFilter
import com.manoffocus.mfrickandmorty.components.mficon.MFIcon
import com.manoffocus.mfrickandmorty.components.mficon.MFIconSize
import com.manoffocus.mfrickandmorty.components.mflottie.MFIconLoader
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.models.db.CharacterLike
import com.manoffocus.mfrickandmorty.models.db.User
import com.manoffocus.mfrickandmorty.navigation.MFScreens
import com.manoffocus.mfrickandmorty.ui.theme.mfCharacterScreenFavouriteColor
import com.manoffocus.mfrickandmorty.ui.theme.verticalPaddingBg

@Composable
fun MFTopBar(
    backgroundColor : Color =  MaterialTheme.colors.background,
    user: User? = null,
    actualScreen: MFScreens = MFScreens.MFHomeScreen,
    onUserProfileClick: () -> Unit = {},
    onFavouriteClick: () -> Unit = {},
    onDeleteTextSearch: () -> Unit = {},
    onSearch: () -> Unit = {},
    onSearchButtonClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    likedCharacter: CharacterLike? = null,
    searchText: MutableState<String> = mutableStateOf("")
) {
    TopAppBar(
        modifier = Modifier
            .padding(vertical = verticalPaddingBg)
            .height(80.dp)
            .fillMaxWidth()
            .background(backgroundColor),
        navigationIcon = {
            TopBarRow(
                modifier = Modifier,
                backgroundColor = backgroundColor
            ) {
                when(actualScreen){
                    MFScreens.MFHomeScreen -> {
                        user?.let { us ->
                            MFCharacterAvatar(
                                size = MFCharacterAvatarSize.SMALL,
                                characterUrl = us.avatarUrl
                            ){
                                onUserProfileClick.invoke()
                            }
                        } ?: run {
                            MFIconLoader()
                        }
                    } else -> {
                        MFIcon(
                            modifier = Modifier,
                            iconVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.mf_topbar_arrow_back_label),
                            size = MFIconSize.SMALL
                        ){
                            onBackClick.invoke()
                        }
                    }
                }
            }
        },
        title = {
            when(actualScreen){
                MFScreens.MFUserProfileScreen -> {
                    TopBarRow(
                        modifier = Modifier.fillMaxWidth(1F),
                        backgroundColor = backgroundColor
                    ) {
                        MFText(
                            text = stringResource(id = R.string.mf_user_profile_screen_title),
                            align = TextAlign.Center
                        )
                    }
                }
                MFScreens.MFSearchScreen -> {
                    MFUserForm(
                        inputs = arrayOf(
                            MFUserFormInputData(
                                label = stringResource(id = R.string.mf_topbar_search_hint_label),
                                stateValue = searchText,
                                type = KeyboardType.Text,
                                weight = 1F,
                                showTrailingIcon = false,
                                filters = arrayOf(
                                    MFUserFormInputFilter.LengthMax(20),
                                    MFUserFormInputFilter.LettersOnly(),
                                ), onValueChanges = { new, prev ->

                                }, onErrors = {

                                }
                            )
                        ),
                    ){  valid ->
                        if (valid){
                            onSearch.invoke()
                        }
                    }
                }
                else -> {
                    user?.let { us ->
                        TopBarRow(
                            modifier = Modifier.fillMaxWidth(1F),
                            backgroundColor = backgroundColor
                        ) {
                            MFText(
                                text = us.name
                            )
                        }
                    }
                }
            }
        },
        actions = {
            TopBarRow(
                modifier = Modifier
                    .fillMaxWidth(0.2F),
                backgroundColor = backgroundColor
            ) {
                when (actualScreen){
                    MFScreens.MFCharacterScreen -> {
                        MFIcon(
                            modifier = Modifier,
                            iconVector = Icons.Default.Favorite,
                            contentDescription = stringResource(id = R.string.mf_topbar_like_label),
                            tint = if (likedCharacter == null) MaterialTheme.colors.onPrimary else mfCharacterScreenFavouriteColor,
                            size = MFIconSize.SMALL
                        ){
                            onFavouriteClick.invoke()
                        }
                    }
                    MFScreens.MFSearchScreen -> {
                        MFIcon(
                            modifier = Modifier,
                            iconVector = Icons.Default.Clear,
                            contentDescription = stringResource(id = R.string.mf_topbar_delete_label),
                            size = MFIconSize.SMALL
                        ){
                            onDeleteTextSearch.invoke()
                        }
                    }
                    MFScreens.MFHomeScreen -> {
                        MFIcon(
                            modifier = Modifier,
                            iconVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.mf_topbar_icon_search_label),
                            size = MFIconSize.SMALL
                        ){
                            onSearchButtonClick.invoke()
                        }
                    }
                    else -> {

                    }
                }
            }
        },
        backgroundColor = backgroundColor,
        elevation = 0.dp
    )
}
@Composable
fun TopBarRow(
    modifier: Modifier,
    backgroundColor: Color = MaterialTheme.colors.background,
    content: @Composable() (() -> Unit)
){
    Row(
    modifier = modifier
        .fillMaxHeight()
        .background(backgroundColor),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center
    ) {
        content()
    }
}