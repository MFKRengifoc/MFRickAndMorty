package com.manoffocus.mfrickandmorty.components.mfquiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.components.mfsection.MFSectionForVertical
import com.manoffocus.mfrickandmorty.components.mfswiper.MFCardOptionSwiper
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTexSizes
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTextTitle
import com.manoffocus.mfrickandmorty.models.quiz.MFQuestion
import com.manoffocus.mfrickandmorty.ui.theme.horizontalPaddingBg
import com.manoffocus.mfrickandmorty.ui.theme.verticalPaddingBg

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
    Column(
        verticalArrangement = Arrangement.Top
    ) {
        MFSectionForVertical(
            modifier = colModifier.weight(0.2F),
            verticalArrangementC = Arrangement.spacedBy(
                space = 20.dp
            )
        ) {
            MFQuizMarks(modifier = Modifier, actualQuestion = actualQuestion.value, totalQuestions = questions.size)
            MFText(
                text = questions[actualQuestion.value].question,
                size = MFTexSizes.LARGE,
                align = TextAlign.Center
            )
        }
        MFSectionForVertical(
            modifier = colModifier
                .padding(vertical = verticalPaddingBg * 3)
                .weight(0.5F)
        ) {
            MFCardOptionSwiper(
                modifier = Modifier,
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
        Box(modifier = Modifier
            .fillMaxSize()
        ){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(question.image)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.mf_loading_avatar_icon),
                contentDescription = stringResource(R.string.mf_cd_placeholder_character) + question.descriptionCorrect,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize(),
                error = painterResource(id = R.drawable.mf_alert_icon)
            )
            val rowMod = Modifier
                .fillMaxWidth()
            Column(
                modifier = rowMod
                    .fillMaxHeight()
                    .background(brush = Brush.verticalGradient(
                        colors = arrayListOf(
                            Color.Transparent,
                            MaterialTheme.colors.background.copy(alpha = 0.3F),
                            Color.Black
                        )
                    )),
                verticalArrangement = Arrangement.Bottom
            ) {
                MFSectionForVertical(
                    modifier = Modifier
                ) {
                    Row(
                        modifier = rowMod
                            .height(IntrinsicSize.Max),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(0.5F),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(
                                space = 10.dp,
                                alignment = Alignment.CenterVertically
                            )
                        ) {
                            MFText(
                                text = question.options[0],
                                align = TextAlign.Center
                            )
                            Image(
                                painter = painterResource(id = R.drawable.mf_option_left_icon),
                                contentDescription = question.options[0],
                                modifier = Modifier.size(60.dp)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(0.5F),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(
                                space = 10.dp,
                                alignment = Alignment.CenterVertically
                            )
                        ) {
                            MFText(
                                text = question.options[1],
                                align = TextAlign.Center
                            )
                            Image(
                                painter = painterResource(id = R.drawable.mf_option_right_icon),
                                contentDescription = question.options[1],
                                modifier = Modifier.size(60.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}