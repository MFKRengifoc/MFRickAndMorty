package com.manoffocus.mfrickandmorty.components.mfswiper

import android.util.Log
import android.view.MotionEvent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.components.mfbutton.MFButton
import com.manoffocus.mfrickandmorty.components.mfcharactersguard.MFCharacterGuard
import com.manoffocus.mfrickandmorty.components.mfquiz.MFQuizAvatarAndQuestion
import com.manoffocus.mfrickandmorty.components.mfsection.MFSectionForVertical
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTexSizes
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTextTitle
import com.manoffocus.mfrickandmorty.models.quiz.MFQuestion
import com.manoffocus.mfrickandmorty.ui.theme.*
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.math.log

enum class SwipeDirection {
    LEFT,
    RIGHT,
    NONE
}

@Composable
fun rememberSwipeState(): MFSwiper {
    return remember { MFSwiper() }
}

class MFSwiper {
    private var initialOffset: Offset? = null
    private var lastSwipeDirection = SwipeDirection.NONE
    private var amount = 0.0
    private var threshold = 100

    fun onSwipe(event: MotionEvent, listener: (SwipeDirection, Double, Float, Boolean, SwipeDirection) -> Unit) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                initialOffset = Offset(event.x, event.y)
            }
            MotionEvent.ACTION_MOVE -> {
                initialOffset?.let { offset ->
                    try {
                        val deltaX = event.x - offset.x
                        amount += deltaX.toDouble() * 0.1
                        var direction = SwipeDirection.NONE
                        var rotation = 0.0F
                        when {
                            amount > 0 -> {
                                direction = SwipeDirection.RIGHT
                                rotation = log(abs(amount) * 2, 2.0).toFloat()
                            }
                            amount < 0 -> {
                                direction = SwipeDirection.LEFT
                                rotation = -(log(abs(amount) * 2, 2.0).toFloat())
                            }
                        }
                        lastSwipeDirection = direction
                        val hasReachedThreshold = (abs(amount) > threshold)
                        if (abs(rotation) > 0){
                            listener.invoke(direction, amount, rotation, hasReachedThreshold, lastSwipeDirection)
                        }
                    } catch (ex: Exception) {
                        Log.w(TAG, "E: ${ex}")
                        ex.printStackTrace()
                        listener.invoke(SwipeDirection.NONE, 0.0, 0F, false, SwipeDirection.NONE)
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                val hasReachedThreshold = (abs(amount) > threshold)
                listener.invoke(SwipeDirection.NONE, 0.0, 0F, hasReachedThreshold, lastSwipeDirection)
                initialOffset = null
                amount = 0.0
            }
        }
    }
    companion object {
        const val TAG = "MFSwiper"
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MFCardOptionSwiper(
    modifier: Modifier = Modifier,
    question: MFQuestion,
    onNext : (Boolean) -> Unit
){
    val chosenOption = remember{ mutableStateOf("") }
    val onSwipingOption = remember { mutableStateOf("") }
    val colorBg = when {
        chosenOption.value == "" -> MaterialTheme.colors.onPrimary
        chosenOption.value == question.correct -> MaterialTheme.colors.primaryVariant
        chosenOption.value != question.correct -> darkError
        else -> {
            MaterialTheme.colors.primaryVariant
        }
    }
    var alreadyInitAnimate = false
    val swipeState = rememberSwipeState()
    val position = remember { mutableStateOf(0.dp) }
    val rotate = remember { mutableStateOf(0.0F) }
    val rot by animateFloatAsState(targetValue = rotate.value)
    val pos by animateDpAsState(targetValue = position.value)
    if (!alreadyInitAnimate){
        LaunchedEffect(key1 = 1, block = {
            delay(400)
            position.value = 20.dp
            rotate.value = 2F
            delay(300)
            position.value = -20.dp
            rotate.value = -2F
            delay(300)
            position.value = 0.dp
            rotate.value = 0F
            alreadyInitAnimate = true
        })
    }
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = topBottomPaddingBg * 4)
                .weight(0.1F),
            contentAlignment = Alignment.Center
        ){
            if (onSwipingOption.value != ""){
                MFText(
                    text = stringResource(id = R.string.mf_quiz_screen_choosing_answers_label, onSwipingOption.value)
                )
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.9F)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            rickFirstColor,
                            rickSecondColor
                        )
                    ),
                    shape = RoundedCornerShape(corner = CornerSize(mfSwiperCornerSize))
                )
                .offset(x = pos)
                .graphicsLayer {
                    rotationZ = rot
                }
                .pointerInteropFilter {
                    if (chosenOption.value == "") {
                        swipeState.onSwipe(it) { swipeDirection, amountDrag, rotation, hasReachedThreshold, lastSwipeDirection ->
                            when (swipeDirection) {
                                SwipeDirection.NONE -> {
                                    position.value = amountDrag.dp
                                    rotate.value = 0.0F
                                    when {
                                        hasReachedThreshold && lastSwipeDirection == SwipeDirection.LEFT -> {
                                            chosenOption.value = question.options[0]
                                        }

                                        hasReachedThreshold && lastSwipeDirection == SwipeDirection.RIGHT -> {
                                            chosenOption.value = question.options[1]
                                        }

                                        else -> {
                                            onSwipingOption.value = ""
                                        }
                                    }
                                }

                                else -> {
                                    position.value = amountDrag.dp
                                    rotate.value = rotation
                                    when (swipeDirection) {
                                        SwipeDirection.LEFT -> {
                                            onSwipingOption.value = question.options[0]
                                        }

                                        SwipeDirection.RIGHT -> {
                                            onSwipingOption.value = question.options[1]
                                        }

                                        else -> {
                                            onSwipingOption.value = ""
                                        }
                                    }
                                }
                            }
                        }
                        true
                    } else {
                        false
                    }
                },
            elevation = 20.dp,
            shape = RoundedCornerShape(corner = CornerSize(mfSwiperCornerSize))
        ) {
            Box(
                modifier = modifier
            ){
                val colMod = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                Column(
                    modifier = colMod
                        .background(
                            color = colorBg,
                            shape = RoundedCornerShape(corner = CornerSize(mfSwiperCornerSize))
                        ),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 10.dp,
                        alignment = Alignment.CenterVertically
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (chosenOption.value != ""){
                        val optionRes = when(chosenOption.value == question.correct){
                            true -> {stringResource(id = R.string.mf_quiz_screen_good_option_label)}
                            false -> {stringResource(id = R.string.mf_quiz_screen_bad_option_label)}
                        }
                        val text = remember(optionRes) {
                            mutableStateOf(optionRes)
                        }
                        MFCharacterGuard(
                            msg = text
                        )
                        MFTextTitle(
                            text = stringResource(id = R.string.mf_quiz_screen_correct_answer_label)
                        )
                        MFText(
                            text = question.correct,
                        )
                        MFText(
                            text = question.descriptionCorrect,
                            size = MFTexSizes.SMALL,
                            color = MaterialTheme.colors.onPrimary
                        )
                        MFButton(
                            text = stringResource(id = R.string.mf_quiz_screen_next_question_label)
                        ){
                            onNext.invoke(chosenOption.value == question.correct)
                            chosenOption.value = ""
                            onSwipingOption.value = ""
                        }
                    }
                }
                if (chosenOption.value == ""){
                    val rowMod = Modifier
                        .fillMaxWidth()
                    Column(
                        modifier = colMod
                            .fillMaxHeight()
                            .padding(bottom = topBottomPaddingBg * 3)
                            .background(
                                color = MaterialTheme.colors.background
                            )
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colors.primaryVariant,
                                shape = RoundedCornerShape(
                                    topStart = CornerSize(mfSwiperCornerSize),
                                    topEnd = CornerSize(mfSwiperCornerSize),
                                    bottomEnd = CornerSize(0.dp),
                                    bottomStart = CornerSize(0.dp)
                                )
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        MFSectionForVertical(modifier = rowMod) {
                            MFQuizAvatarAndQuestion(modifier = Modifier, question = question)
                        }
                    }
                }
            }
        }
    }
}