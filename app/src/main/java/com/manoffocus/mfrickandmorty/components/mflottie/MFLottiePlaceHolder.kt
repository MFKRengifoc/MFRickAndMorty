package com.manoffocus.mfrickandmorty.components.mflottie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition


enum class MFLoadingPlaceHolderSize(var size: Dp){
    LARGE(250.dp),
    MEDIUM(220.dp),
    SMALL(150.dp),
    XSMALL(50.dp);
}

@Composable
fun MFLoadingPlaceHolder(
    modifier: Modifier = Modifier,
    placeholder: Int,
    speed: Float = 1F,
    size: MFLoadingPlaceHolderSize
) {
    Row(
        modifier = modifier.height(size.size),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(placeholder))
        LottieAnimation(
            composition,
            iterations = LottieConstants.IterateForever,
            speed = speed,
            modifier = Modifier.fillMaxSize()
        )
    }
}