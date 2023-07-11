package com.manoffocus.mfrickandmorty.screens.mfquiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.components.mfbutton.MFButton
import com.manoffocus.mfrickandmorty.components.mfbutton.MFButtonSize
import com.manoffocus.mfrickandmorty.components.mfcharactersguard.MFCharacterGuard
import com.manoffocus.mfrickandmorty.components.mfcharactersguard.MFCharacterMsgSize
import com.manoffocus.mfrickandmorty.components.mfdialog.MFDialog
import com.manoffocus.mfrickandmorty.components.mflottie.MFLoadingPlaceHolder
import com.manoffocus.mfrickandmorty.components.mflottie.MFLoadingPlaceHolderSize
import com.manoffocus.mfrickandmorty.components.mfquiz.MFQuizInfo
import com.manoffocus.mfrickandmorty.components.mfquiz.MFQuizQuestions
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTexSizes
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTexTitleSizes
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTextTitle
import com.manoffocus.mfrickandmorty.components.mftopbar.MFTopBar
import com.manoffocus.mfrickandmorty.navigation.MFScreens
import com.manoffocus.mfrickandmorty.ui.theme.darkError
import com.manoffocus.mfrickandmorty.ui.theme.topBottomPaddingBg
import com.manoffocus.mfrickandmorty.ui.theme.verticalPaddingBg
import java.util.Date

@Composable
fun MFQuizScreen(
    navController: NavController,
    mfQuizViewModel: MFQuizViewModel = hiltViewModel()
) {
    val user = mfQuizViewModel.user.value
    val quiz = mfQuizViewModel.quiz.value
    val finishedQuiz = remember { mutableStateOf(false) }
    val correctQuestions = remember { mutableStateOf(0) }
    val preparedButtonClicked = remember { mutableStateOf(false) }
    var timeStarted : Date? = null
    val leavingQuiz = remember { mutableStateOf(false) }
    mfQuizViewModel.getJsonDataFromAsset(LocalContext.current)
    Scaffold(
        modifier = Modifier.background(MaterialTheme.colors.background),
        topBar = {
            MFTopBar(
                user = user,
                actualScreen = MFScreens.MFQuizScreen,
                onBackClick = {
                    if (preparedButtonClicked.value){
                        leavingQuiz.value = true
                    } else {
                        navController.popBackStack()
                    }
                }
            )
        }
    ) { it ->
        if (leavingQuiz.value){
            MFDialog(
                title = stringResource(id = R.string.mf_quiz_screen_leave_quiz_title)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = verticalPaddingBg),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    val msg = stringResource(id = R.string.mf_quiz_screen_leave_quiz_ask)
                    val mutableMsg = remember {
                        mutableStateOf(msg)
                    }
                    MFCharacterGuard(
                        msg = mutableMsg,
                        icon = R.drawable.mf_morty_icon,
                        dialogSize = MFCharacterMsgSize.SMALL
                    )
                    MFText(
                        modifier = Modifier.padding(top = topBottomPaddingBg),
                        text = stringResource(id = R.string.mf_quiz_screen_leave_quiz_content),
                        size = MFTexSizes.SMALL,
                        color = MaterialTheme.colors.secondary
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = verticalPaddingBg)
                            .padding(vertical = verticalPaddingBg),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                        ) {
                            MFButton(
                                modifier = Modifier
                                    .width(MFButtonSize.SMALL.w),
                                text = stringResource(id = R.string.mf_quiz_screen_confirm_dialog_label),
                                size = MFButtonSize.SMALL,
                                color = darkError
                            ){
                                navController.popBackStack()
                            }
                            MFButton(
                                modifier = Modifier
                                    .width(MFButtonSize.SMALL.w),
                                text = stringResource(id = R.string.mf_quiz_screen_cancel_dialog_label),
                                size = MFButtonSize.SMALL,
                            ){
                                leavingQuiz.value = false
                            }
                        }
                    }
                }
            }
        }
        Surface(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(it)
                .padding(start = topBottomPaddingBg)
                .padding(vertical = verticalPaddingBg)
                .fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colors.background)
            ) {
                if (!preparedButtonClicked.value){
                    MFQuizInfo {
                        timeStarted = Date()
                        preparedButtonClicked.value = true
                    }
                } else {
                    if (!finishedQuiz.value){
                        quiz.questions?.let { qList ->
                            MFQuizQuestions(questions = qList, correctQuestions = correctQuestions){
                                finishedQuiz.value = true
                                user?.let { us ->
                                    timeStarted?.let { timeS ->
                                        mfQuizViewModel.insertQuiz(
                                            totalQuestions = qList.size,
                                            passedQuestions = correctQuestions.value,
                                            timestampStarted = timeS,
                                            userId = us.userId!!
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                      Column(
                          modifier = Modifier
                              .fillMaxHeight(),
                          verticalArrangement = Arrangement.Center,
                          horizontalAlignment = Alignment.CenterHorizontally
                      ) {
                          val testPassed = (correctQuestions.value > 3)
                          var lottie = R.raw.mf_morty_dancing_lottie
                          var color = MaterialTheme.colors.primary
                          var title = stringResource(id = R.string.mf_quiz_screen_test_passed_label)
                          var text = stringResource(id = R.string.mf_quiz_screen_test_passed_expl_label)
                          if (!testPassed){
                              lottie = R.raw.mf_morty_crying_lottie
                              title = stringResource(id = R.string.mf_quiz_screen_test_failed_label)
                              text = stringResource(id = R.string.mf_quiz_screen_test_failed_expl_label)
                              color = darkError
                          }
                          MFLoadingPlaceHolder(
                              modifier = Modifier
                                  .padding(vertical = verticalPaddingBg),
                              placeholder = lottie,
                              size = MFLoadingPlaceHolderSize.LARGE,
                          )
                          MFTextTitle(
                              modifier = Modifier
                                  .padding(vertical = verticalPaddingBg),
                              text = title,
                              size = MFTexTitleSizes.LARGE,
                              maxWidth = 300.dp
                          )
                          MFText(
                              text = text,
                              color = MaterialTheme.colors.secondary,
                              size = MFTexSizes.MEDIUM
                          )
                          Row(
                              modifier = Modifier
                                  .fillMaxWidth()
                                  .padding(vertical = verticalPaddingBg)
                                  .padding(bottom = topBottomPaddingBg),
                              horizontalArrangement = Arrangement.Center
                          ) {
                              quiz.questions?.let { qList ->
                                  MFText(
                                      text = stringResource(id = R.string.mf_quiz_screen_passed_questions_label),
                                      size = MFTexSizes.LARGE
                                  )
                                  MFText(
                                      text = "${correctQuestions.value}",
                                      color = color,
                                      size = MFTexSizes.LARGE
                                  )
                                  MFText(
                                      text = "/",
                                      size = MFTexSizes.LARGE
                                  )
                                  MFText(
                                      text = "${qList.size}",
                                      size = MFTexSizes.LARGE
                                  )
                              }
                          }
                          MFButton(
                              text = stringResource(id = R.string.mf_quiz_screen_return_home_label),
                              icon = Icons.Default.Home
                          ){
                              navController.popBackStack()
                          }
                      }
                    }
                }
            }
        }
    }
}