package com.manoffocus.mfrickandmorty.screens.mfsearch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

@Composable
fun MFSearchScreen(
    navController: NavController,
    mfSearchViewModel: MFSearchViewModel,
    connectedStatus: MutableState<Pair<String, Boolean>>
) {
    val searchText = rememberSaveable {
        mutableStateOf("")
    }
    val searches = mfSearchViewModel.searchList.value
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
                    mfSearchViewModel.clear()
                    navController.popBackStack()
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
                    MFSectionForVertical(modifier = rowMod) {
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
                                    data.results?.let { results ->
                                        for (res in results){
                                            MFCharacterFound(
                                                character = res
                                            ){
                                                navController.navigate(MFScreens.MFCharacterScreen.name + "/${res.id}")
                                            }
                                        }
                                    } ?: run {
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