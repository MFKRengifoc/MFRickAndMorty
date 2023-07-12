package com.manoffocus.mfrickandmorty.screens.mfsplash

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTextTitle
import com.manoffocus.mfrickandmorty.models.db.User
import com.manoffocus.mfrickandmorty.navigation.MFScreens
import com.manoffocus.mfrickandmorty.ui.theme.mfSplashScreenSubTitleColor
import com.manoffocus.mfrickandmorty.ui.theme.mfSplashScreenTitleColor
import kotlinx.coroutines.delay

@Composable
fun MFSplashScreen(
    navController: NavController,
    user: User?
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        var animate by remember {
            mutableStateOf(false)
        }
        var defaultScale = 0.dp
        val animateScale by animateDpAsState(
            targetValue = if (animate) 1.5.dp else defaultScale,
            animationSpec = tween( durationMillis = 500)
        )
        LaunchedEffect(Unit, block = {
            delay(100)
            animate = true
            delay(2500)
            user?.let{ us ->
                navController.navigate(MFScreens.MFHomeScreen.name){
                    popUpTo(MFScreens.MFSplashScreen.name){
                        inclusive = true
                    }
                }
            } ?: run  {
                navController.navigate(MFScreens.MFProfilerScreen.name){
                    popUpTo(MFScreens.MFSplashScreen.name){
                        inclusive = true
                    }
                }
            }
        })
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.mf_splashscreen_glood_lottie))
            Box(
                modifier = Modifier
                    .size(500.dp)
                    .scale(animateScale.value)
            ){
                LottieAnimation(
                    composition,
                    iterations = LottieConstants.IterateForever,
                    speed = 0.9F
                )
            }
            MFTextTitle(
                text = stringResource(id = R.string.mf_splash_screen_title),
                color = mfSplashScreenTitleColor
            )
            MFText(
                text = stringResource(id = R.string.mf_splash_screen_subtitle),
                color = mfSplashScreenSubTitleColor
            )
        }
    }
}