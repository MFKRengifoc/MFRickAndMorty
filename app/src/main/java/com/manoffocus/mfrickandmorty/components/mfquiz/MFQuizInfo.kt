package com.manoffocus.mfrickandmorty.components.mfquiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.components.mfbutton.MFButton
import com.manoffocus.mfrickandmorty.components.mfcharactersguard.MFCharacterGuard
import com.manoffocus.mfrickandmorty.components.mfcharactersguard.MFCharacterMsgSize
import com.manoffocus.mfrickandmorty.components.mfsection.MFSectionForVertical
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTextAppend
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTextAppends
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTextTitle
import com.manoffocus.mfrickandmorty.ui.theme.mfFanInfoScreenTitleColor
import com.manoffocus.mfrickandmorty.ui.theme.verticalPaddingBg

@Composable
fun MFQuizInfo(
    questionsSize: Int,
    onPrepared: () -> Unit
) {
    val rowModifier = Modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colors.background)
        .padding(vertical = verticalPaddingBg)
    val msg = stringResource(id = R.string.mf_quiz_screen_hi)
    val msgState = remember{
        mutableStateOf(msg)
    }
    Column(

    ) {
        MFSectionForVertical(
            modifier = rowModifier.weight(0.45F)
        ) {
            MFCharacterGuard(
                icon = R.drawable.mf_jerry_icon,
                dialogSize = MFCharacterMsgSize.BIG,
                msg = msgState
            )
        }
        MFSectionForVertical(
            modifier = rowModifier.weight(0.35F)
        ) {
            Column(
                modifier = rowModifier,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                MFTextTitle(
                    modifier = Modifier
                        .padding(top = verticalPaddingBg),
                    text = stringResource(id = R.string.mf_quiz_screen_title),
                    color = mfFanInfoScreenTitleColor,
                    underLine = true
                )

                val testExpl = stringArrayResource(id = R.array.mf_quiz_screen_explone)
                val fanastimsList = testExpl.map {
                    MFTextAppend(
                        text = it
                    )
                }
                fanastimsList.last().apply {
                    weight = FontWeight.Bold
                }
                MFTextAppends(
                    textArr = fanastimsList
                )
                val testExplTwo = stringArrayResource(id = R.array.mf_quiz_screen_expltwo)
                testExplTwo[1] = "$questionsSize ${testExplTwo[1]}"
                val questionSizeList = testExplTwo.map {
                    MFTextAppend(
                        text = it
                    )
                }
                questionSizeList.last().apply {
                    weight = FontWeight.Bold
                }
                MFTextAppends(
                    textArr = questionSizeList
                )
                MFText(
                    modifier = Modifier
                        .padding(top = verticalPaddingBg)
                        .fillMaxWidth(0.9F),
                    text = stringResource(id = R.string.mf_quiz_screen_explthr, questionsSize/2),
                    align = TextAlign.Center
                )
                MFText(
                    modifier = Modifier
                        .padding(top = verticalPaddingBg)
                        .fillMaxWidth(0.9F),
                    text = stringResource(id = R.string.mf_quiz_screen_explfrh),
                    align = TextAlign.Center
                )
            }
        }
        MFSectionForVertical(
            modifier = rowModifier.weight(0.2F)
        ) {
            MFButton(text = stringResource(id = R.string.mf_quiz_screen_prepared_but)){
                onPrepared.invoke()
            }
        }
    }
}