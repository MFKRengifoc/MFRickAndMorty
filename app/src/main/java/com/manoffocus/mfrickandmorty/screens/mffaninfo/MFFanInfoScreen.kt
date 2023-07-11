package com.manoffocus.mfrickandmorty.screens.mffaninfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.components.mfbutton.MFButton
import com.manoffocus.mfrickandmorty.components.mfcharactersguard.MFCharacterGuard
import com.manoffocus.mfrickandmorty.components.mfcharactersguard.MFCharacterMsgSize
import com.manoffocus.mfrickandmorty.components.mfsection.MFSectionForVertical
import com.manoffocus.mfrickandmorty.components.mfsurface.MFSurface
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTextAppend
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTextAppends
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTextTitle
import com.manoffocus.mfrickandmorty.navigation.MFScreens
import com.manoffocus.mfrickandmorty.ui.theme.mfFanInfoScreenTitleColor
import com.manoffocus.mfrickandmorty.ui.theme.mortySecondColor
import com.manoffocus.mfrickandmorty.ui.theme.rickFirstColor
import com.manoffocus.mfrickandmorty.ui.theme.sidesPaddingBg
import com.manoffocus.mfrickandmorty.ui.theme.topBottomPaddingBg
import com.manoffocus.mfrickandmorty.ui.theme.verticalPaddingBg

@Composable
fun MFFanInfoScreen(
    navController: NavController
) {
    MFSurface(
        modifier = Modifier
            .padding(horizontal = sidesPaddingBg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
        ) {
            val rowModifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.background)
                .padding(vertical = verticalPaddingBg)
            MFSectionForVertical(
                modifier = rowModifier.weight(0.85F)
            ) {
                val msg = stringResource(id = R.string.mf_faninfo_screen_hi)
                val msgState = remember{
                    mutableStateOf(msg)
                }
                Row(
                    modifier = rowModifier,
                    horizontalArrangement = Arrangement.Center
                ) {
                    MFCharacterGuard(
                        icon = R.drawable.mf_morty_icon,
                        dialogSize = MFCharacterMsgSize.BIG,
                        msg = msgState
                    )
                }
                Row(
                    modifier = rowModifier,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = rowModifier,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        MFTextTitle(
                            modifier = Modifier
                                .padding(top = topBottomPaddingBg),
                            text = stringResource(id = R.string.mf_faninfo_screen_title),
                            color = mfFanInfoScreenTitleColor,
                            align = TextAlign.Center,
                            underLine = true
                        )
                        MFText(
                            modifier = Modifier
                                .padding(top = topBottomPaddingBg)
                                .fillMaxWidth(0.9F),
                            text = stringResource(id = R.string.mf_faninfo_screen_expl_one),
                            align = TextAlign.Center
                        )
                        val rickMortyText = stringArrayResource(id = R.array.mf_faninfo_rickmorty_title)
                        val rickMortyList = rickMortyText.map {
                            MFTextAppend(
                                text = it,
                                weight = FontWeight.Bold
                            )
                        }
                        rickMortyList.first().apply {
                            color = rickFirstColor
                        }
                        rickMortyList[rickMortyList.size - 2].apply {
                            color = mortySecondColor
                        }
                        MFTextAppends(
                            textArr = rickMortyList
                        )
                        MFText(
                            modifier = Modifier
                                .padding(top = verticalPaddingBg)
                                .fillMaxWidth(0.9F),
                            text = stringResource(id = R.string.mf_faninfo_screen_expl_two),
                            align = TextAlign.Center
                        )
                        val jerrysText = stringArrayResource(id = R.array.mf_faninfo_screen_last_expl)
                        val jerrysList = jerrysText.map {
                            MFTextAppend(text = it)
                        }
                        jerrysList.last().apply {
                            color = MaterialTheme.colors.secondary
                            weight = FontWeight.Bold
                        }
                        MFTextAppends(
                            modifier = Modifier
                                .padding(top = verticalPaddingBg)
                                .fillMaxWidth(0.9F),
                            textArr = jerrysList
                        )
                    }
                }
            }
            MFSectionForVertical(
                modifier = rowModifier.weight(0.15F)
            ) {
                MFButton(text = stringResource(id = R.string.mf_faninfo_screen_expl_screen_continue_ask_label)){
                    navController.navigate(MFScreens.MFHomeScreen.name){
                        popUpTo(MFScreens.MFFanInfoScreen.name){
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
}