package com.manoffocus.mfrickandmorty.components.mfquiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.manoffocus.mfrickandmorty.components.mfcharactersgrid.MFCharacterAvatar
import com.manoffocus.mfrickandmorty.components.mfcharactersgrid.MFCharacterAvatarSize
import com.manoffocus.mfrickandmorty.components.mfswiper.MFCardOptionSwiper
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTextTitle
import com.manoffocus.mfrickandmorty.models.quiz.MFQuestion
import com.manoffocus.mfrickandmorty.ui.theme.horizontalPaddingBg
import com.manoffocus.mfrickandmorty.ui.theme.sidesPaddingBg
import com.manoffocus.mfrickandmorty.ui.theme.topBottomPaddingBg

@Composable
fun MFQuizQuestions(
    questions: List<MFQuestion>,
    correctQuestions: MutableState<Int>,
    onCompleteQuiz: () -> Unit
) {
    val actualQuestion = remember { mutableStateOf(0) }
    val colModifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = horizontalPaddingBg)
    Row(
        modifier = colModifier
            .fillMaxHeight(0.4F)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val avatarRow = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPaddingBg)
            MFQuizMarks(modifier = avatarRow, actualQuestion = actualQuestion.value, totalQuestions = questions.size)
            MFQuizAvatarAndQuestion(modifier = avatarRow, question = questions[actualQuestion.value])
        }
    }
    Row(
        modifier = colModifier
            .fillMaxHeight()
    ) {
        val answersRow = Modifier
            .fillMaxWidth()
        Column(
            modifier = answersRow
                .fillMaxHeight()
                .padding(
                    horizontal = sidesPaddingBg,
                    vertical = topBottomPaddingBg
                ),
        ) {
            Row(
                modifier = answersRow
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = answersRow
                        .padding(all = horizontalPaddingBg),

                    ) {
                    MFCardOptionSwiper(
                        modifier = Modifier.fillMaxWidth(),
                        question = questions[actualQuestion.value]
                    ){ questionCorrect ->
                        if (questionCorrect){
                            correctQuestions.value++
                        }
                        if (actualQuestion.value < questions.size - 1){
                            actualQuestion.value++

                        } else {
                            onCompleteQuiz.invoke()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MFQuizMarks(
    modifier: Modifier,
    actualQuestion: Int,
    totalQuestions: Int
){
    Row(
        modifier = modifier
            .fillMaxHeight(0.1F),
        horizontalArrangement = Arrangement.Start
    ) {
        MFTextTitle(
            text = "${(actualQuestion+ 1)}"
        )
        MFTextTitle(
            text = "/"
        )
        MFTextTitle(
            text = "$totalQuestions"
        )
    }
}

@Composable
fun MFQuizAvatarAndQuestion(
    modifier: Modifier,
    question: MFQuestion
){
    Row(
        modifier = modifier
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MFCharacterAvatar(
                size = MFCharacterAvatarSize.LARGE,
                characterUrl = question.image
            ){

            }
            MFText(
                text = question.question
            )
        }
    }
}