package com.manoffocus.mfrickandmorty.components.mfuserprofilecontent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.commons.Utils
import com.manoffocus.mfrickandmorty.components.mficon.MFIcon
import com.manoffocus.mfrickandmorty.components.mfscrollviews.MFVertical
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTextTitle
import com.manoffocus.mfrickandmorty.models.db.Quiz
import com.manoffocus.mfrickandmorty.ui.theme.darkError
import com.manoffocus.mfrickandmorty.ui.theme.rickSecondColor
import com.manoffocus.mfrickandmorty.ui.theme.sidesPaddingBg

@Composable
fun MFQuizzesContent(
    modifier: Modifier,
    quizzes: List<Quiz>
){
    MFVertical(
        modifier = modifier
            .fillMaxWidth(),
        list = quizzes
    ) { q ->
        val quiz = q as Quiz
        Card(
            modifier = modifier
                .height(100.dp)
                .fillMaxWidth()
                .padding(horizontal = sidesPaddingBg)
                .clip(RoundedCornerShape(corner = CornerSize(5.dp))),
            elevation = 0.dp,
            backgroundColor = Color.Transparent
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val concatQuestions = "${quiz.passedQuestions}/${quiz.totalQuestions}"
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.7F)
                        .fillMaxHeight(),
                ) {
                    MFTextTitle(
                        text = stringResource(id = R.string.mf_user_profile_screen_quiz_number_label, "${quiz.quizId}"),
                        underLine = true
                    )
                    MFText(
                        text = Utils.formatDateToHumans(quiz.timestampFinished),
                        color = MaterialTheme.colors.primary
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MFTextTitle(
                            text = concatQuestions
                        )
                        MFIcon(
                            modifier = Modifier,
                            contentDescription = stringResource(id = R.string.mf_user_profile_screen_quiz_cd_label, quiz.passedQuestions, quiz.totalQuestions),
                            iconVector = if (quiz.passedQuestions > 3) Icons.Default.Check else Icons.Default.Clear,
                            tint = if (quiz.passedQuestions > 3) rickSecondColor else darkError
                        )
                    }
                }
            }
        }
    }
}