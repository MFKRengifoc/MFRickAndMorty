package com.manoffocus.mfrickandmorty.components.mfsurface

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.manoffocus.mfrickandmorty.ui.theme.topBottomPaddingBg

@Composable
fun MFSurface(
    modifier: Modifier,
    backgroundColor: Color = MaterialTheme.colors.background,
    content: @Composable() () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .padding(top = topBottomPaddingBg),
        color = backgroundColor,
        contentColor = backgroundColor
    ) {
        content()
    }
}