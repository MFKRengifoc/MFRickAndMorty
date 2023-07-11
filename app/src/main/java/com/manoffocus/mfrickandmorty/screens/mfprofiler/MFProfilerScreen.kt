package com.manoffocus.mfrickandmorty.screens.mfprofiler

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.components.mfbutton.MFButton
import com.manoffocus.mfrickandmorty.components.mfcharactersgrid.MFCharacterGrid
import com.manoffocus.mfrickandmorty.components.mfcharactersguard.MFCharacterGuard
import com.manoffocus.mfrickandmorty.components.mfcharactersguard.MFCharacterMsgSize
import com.manoffocus.mfrickandmorty.components.mfform.MFUserForm
import com.manoffocus.mfrickandmorty.components.mfform.MFUserFormInputData
import com.manoffocus.mfrickandmorty.components.mfform.MFUserFormInputFilter
import com.manoffocus.mfrickandmorty.components.mfsection.MFSectionForVertical
import com.manoffocus.mfrickandmorty.components.mfsurface.MFSurface
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTexSizes
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTexTitleSizes
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTextTitle
import com.manoffocus.mfrickandmorty.navigation.MFScreens
import com.manoffocus.mfrickandmorty.ui.theme.horizontalPaddingBg
import com.manoffocus.mfrickandmorty.ui.theme.topBottomPaddingBg
import com.manoffocus.mfrickandmorty.ui.theme.verticalPaddingBg

@Composable
fun MFProfilerScreen(
    navController: NavController,
    viewModel: MFProfilerViewModel = hiltViewModel()
) {
    val onClickedContinue = rememberSaveable { mutableStateOf(false) }
    val validText = rememberSaveable { mutableStateOf("") }
    val ageStateValue = rememberSaveable { mutableStateOf("") }
    val chosenAvatar = rememberSaveable { mutableStateOf(-1) }
    val chosenAvatarUrl = rememberSaveable { mutableStateOf("") }
    val validForm = rememberSaveable{ mutableStateOf(false) }
    LaunchedEffect(Unit){
        viewModel.loadFirstCharacters()
    }
    MFSurface(
        modifier = Modifier
            .padding(horizontal = horizontalPaddingBg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.background)
        ) {
            val rowModifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.background)
                .padding(vertical = verticalPaddingBg)
            MFSectionForVertical(
                modifier = rowModifier.weight(0.8F),
                verticalAlignmentR = Alignment.Top,
                verticalArrangementC = Arrangement.spacedBy(5.dp)
            ) {
                Row(modifier = rowModifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MFTextTitle(
                        text = stringResource(id = R.string.mf_profiler_screen_title),
                        underLine = true
                    )
                    MFCharacterGuard(
                        icon = R.drawable.mf_rick_icon,
                        dialogSize = MFCharacterMsgSize.SMALL
                    )
                }
                Row(
                    modifier = rowModifier
                ) {
                    MFTextTitle(
                        text = stringResource(id = R.string.mf_profiler_screen_who_are_you_label),
                        size = MFTexTitleSizes.MEDIUM
                    )
                }
                MFUserForm(
                    modifier = rowModifier,
                    inputs = arrayOf(
                        MFUserFormInputData(
                            label = stringResource(id = R.string.mf_profiler_screen_name_input_label),
                            stateValue = validText,
                            type = KeyboardType.Text,
                            weight = 0.6F,
                            filters = arrayOf(
                                MFUserFormInputFilter.LengthMin(3),
                                MFUserFormInputFilter.LengthMax(10),
                                MFUserFormInputFilter.LettersOnly(),
                                MFUserFormInputFilter.Required()
                            ), onValueChanges = { new, prev ->

                            }, onErrors = {
                                Log.d("MFProfilerScreen", "${it.toList()}")
                            }
                        ),
                        MFUserFormInputData(
                            label = stringResource(id = R.string.mf_profiler_screen_age_input_label),
                            stateValue = ageStateValue,
                            type = KeyboardType.Number,
                            weight = 0.4F,
                            filters = arrayOf(
                                MFUserFormInputFilter.LengthMin(2),
                                MFUserFormInputFilter.LengthMax(3),
                                MFUserFormInputFilter.Range(14,120),
                                MFUserFormInputFilter.DigitsOnly(),
                                MFUserFormInputFilter.Required()
                            ), onValueChanges = { new, prev ->

                            }, onErrors = {
                                Log.d("MFProfilerScreen", "${it.toList()}")
                            }
                        )
                    ),
                ){  valid ->
                    validForm.value = valid
                }
                if (validForm.value){
                    MFSectionForVertical(
                        modifier = rowModifier,
                        horizontalAlignmentC = Alignment.Start
                    ) {
                        MFTextTitle(
                            text = stringResource(id = R.string.mf_profiler_screen_choose_character_icon_label)
                        )
                        viewModel.firstCharacters.value.data?.let { characters ->
                            MFCharacterGrid(
                                modifier = rowModifier,
                                listOfCharacters = characters,
                                columns = 3
                            ){ character, avatar_url ->
                                chosenAvatar.value = character
                                chosenAvatarUrl.value = avatar_url
                            }
                        }
                    }
                }
            }
            MFSectionForVertical(
                modifier = rowModifier.weight(0.2F)
            ) {
                if (chosenAvatar.value != -1 && validForm.value){
                    MFButton( text = stringResource(id = R.string.mf_profiler_continue_ask_label) ){
                        viewModel.insertUser(
                            name = validText.value,
                            age = ageStateValue.value.toInt(),
                            characterId = chosenAvatar.value,
                            avatarUrl = chosenAvatarUrl.value
                        ){
                            if (it){
                                onClickedContinue.value = true
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(vertical = verticalPaddingBg))
                    MFText(
                        text = stringResource(id = R.string.mf_profiler_screen_no_thanks),
                        color = MaterialTheme.colors.secondary,
                        size = MFTexSizes.SMALL,
                        modifier = Modifier
                            .padding(vertical = topBottomPaddingBg)
                            .clickable { }
                    )
                    if (onClickedContinue.value){
                        navController.navigate(MFScreens.MFFanInfoScreen.name){
                            popUpTo(MFScreens.MFProfilerScreen.name){
                                inclusive = true
                            }
                        }
                        onClickedContinue.value = false
                    }
                }
            }
        }
    }
}
