package com.manoffocus.mfrickandmorty.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.manoffocus.mfrickandmorty.R

val ChocolatePuddingFont = FontFamily(
    Font(R.font.chocolate_pudding)
)
val ChocolatePuddingFontBold = FontFamily(
    Font(R.font.chocolate_pudding, FontWeight.Bold)
)
// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = ChocolatePuddingFont,
    h1 = TextStyle(
        fontFamily = ChocolatePuddingFont,
        fontWeight = FontWeight.Bold
    ),

    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)