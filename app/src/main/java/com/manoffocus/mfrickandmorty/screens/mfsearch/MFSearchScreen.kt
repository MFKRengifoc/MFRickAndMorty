package com.manoffocus.mfrickandmorty.screens.mfsearch

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.components.mfbutton.MFButton
import com.manoffocus.mfrickandmorty.components.mfcharacter.MFCharacterFound
import com.manoffocus.mfrickandmorty.components.mfcharactersguard.MFCharacterGuard
import com.manoffocus.mfrickandmorty.components.mfcharactersguard.MFCharacterMsgSize
import com.manoffocus.mfrickandmorty.components.mfcharactersguard.MFCharacterTextPosition
import com.manoffocus.mfrickandmorty.components.mflottie.MFLoadingPlaceHolder
import com.manoffocus.mfrickandmorty.components.mflottie.MFLoadingPlaceHolderSize
import com.manoffocus.mfrickandmorty.components.mfsearchfilterbar.MFSearchFilterBar
import com.manoffocus.mfrickandmorty.components.mfsection.MFSectionForVertical
import com.manoffocus.mfrickandmorty.components.mfsurface.MFSurface
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.components.mftopbar.MFTopBar
import com.manoffocus.mfrickandmorty.data.Resource
import com.manoffocus.mfrickandmorty.navigation.MFScreens
import com.manoffocus.mfrickandmorty.ui.theme.horizontalPaddingBg
import com.manoffocus.mfrickandmorty.ui.theme.verticalPaddingBg

@Composable
fun MFSearchScreen(
    navController: NavController,
    mfSearchViewModel: MFSearchViewModel,
    connectedStatus: MutableState<Pair<String, Boolean>>,
    searchText: MutableState<String>,
    onBackClick: () -> Unit
) {
    BackHandler {
        onBackClick.invoke()
    }
    val searches = mfSearchViewModel.searchList.value
    val searchItems = mfSearchViewModel.searchItems.value
    val activeNextButton = remember { mfSearchViewModel.activeNextPageButton }
    val listState = rememberSaveable(saver = LazyListState.Saver) {
        LazyListState()
    }
    Scaffold(
        modifier = Modifier.background(MaterialTheme.colors.background),
        topBar = {
            MFTopBar(
                actualScreen = MFScreens.MFSearchScreen,
                onDeleteTextSearch = {
                    searchText.value = ""
                },
                onSearch = {
                    mfSearchViewModel.getCharactersBy(
                        name = searchText.value,
                        status = "",
                        gender = ""
                    )
                },
                searchText = searchText,
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
            val verticalScroll = rememberScrollState()
            val rowMod = Modifier
                .fillMaxWidth()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(verticalScroll),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (connectedStatus.value.second){
                    MFSectionForVertical(modifier = rowMod.padding(vertical = verticalPaddingBg)) {
                        MFSearchFilterBar(
                            modifier = Modifier
                                .fillMaxSize()
                        ){ statusValue, genderValue ->
                            mfSearchViewModel.getCharactersBy(
                                name = searchText.value,
                                status = statusValue,
                                gender = genderValue
                            )
                        }
                    }
                    if (searches is Resource.Loading){
                        MFLoadingPlaceHolder(
                            placeholder = R.raw.mf_loading_planets_lottie,
                            size = MFLoadingPlaceHolderSize.SMALL
                        )
                        MFText(
                            text = stringResource(id = R.string.mf_loader_loading_characters_label)
                        )
                    } else {
                        when(searches){
                            is Resource.Success -> {
                                searches.data?.let { data ->
                                    if (searchItems.isEmpty()){
                                        val mT = stringResource(id = R.string.mf_search_screen_nothing_search_label)
                                        val mortyText = remember {
                                            mutableStateOf(mT)
                                        }
                                        MFCharacterGuard(
                                            modifier = Modifier.padding(top = 20.dp),
                                            icon = R.drawable.mf_morty_icon,
                                            dialogSize = MFCharacterMsgSize.MEDIUM,
                                            msg = mortyText,
                                            textPosition = MFCharacterTextPosition.LEFT
                                        )
                                    } else {
                                        MFText(text = stringResource(id = R.string.mf_search_screen_total_characters_label, searchItems.size))
                                        for (res in searchItems){
                                            MFCharacterFound(
                                                character = res
                                            ){
                                                navController.navigate(MFScreens.MFCharacterScreen.name + "/${res.id}")
                                            }
                                        }
                                        if (activeNextButton.value){
                                            MFButton(
                                                modifier = Modifier.fillMaxWidth(),
                                                text = stringResource(id = R.string.mf_all_locations_screen_more_button_label)
                                            ){
                                                mfSearchViewModel.loadNextPage()
                                            }
                                        }
                                    }
                                }
                            }
                            is Resource.Empty -> {
                                val bT = stringResource(id = R.string.mf_search_screen_wise_search_label)
                                val mortyText = remember {
                                    mutableStateOf(bT)
                                }
                                MFCharacterGuard(
                                    modifier = Modifier.padding(top = 20.dp),
                                    icon = R.drawable.mf_beth_icon,
                                    dialogSize = MFCharacterMsgSize.MEDIUM,
                                    msg = mortyText,
                                    textPosition = MFCharacterTextPosition.LEFT
                                )
                            } else -> {
                                val msgGuard = stringResource(id = R.string.mf_search_screen_nothing_search_label)
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