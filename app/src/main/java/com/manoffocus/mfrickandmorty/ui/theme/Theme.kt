package com.manoffocus.mfrickandmorty.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = darkPrimary,
    primaryVariant = darkPrimaryVariant,
    secondary = darkSecondary,
    secondaryVariant = darkSecondaryVariant,
    onPrimary = darkOnPrimary,
    onSecondary = darkOnSecondary,
    background = darkBackground,
    onBackground = darkOnBackground,
    surface = darkSurface,
    error = darkError
)

@Composable
fun MFRickAndMortyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = DarkColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}