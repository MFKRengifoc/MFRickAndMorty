package com.manoffocus.mfrickandmorty.ui.theme

import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)


val darkPrimary = Color(0xFF5982A3)
val darkOnPrimary = Color(0xFFFFFBFE)
val darkPrimaryVariant = Color(0xFF2B5A80)
val darkSecondary = Color(0xFF76A1BB)
val darkOnSecondary = Color(0xFFFFFBFE)
val darkSecondaryVariant = Color(0xFFBFD0D9)
val darkBackground = Color(0xFF222932)
val darkOnBackground = Color(0xFFFFFBFE)
val darkSurface = Color(0xFF31353C)
val darkError = Color(0xFFE90064)

val rickFirstColor = Color(0xFF76A1BB)
val rickSecondColor = Color(0xFF97CE4C)

val mortyFirstColor = Color(0xFF44281D)
val mortySecondColor = Color(0xFFE4A788)


// MFSplashScreen
val mfSplashScreenTitleColor = Color(0xFF9B5094)
val mfSplashScreenSubTitleColor = Color(0xFF44FFD2)

// MFInput
val mfInputTextColor = darkOnPrimary
val mfInputFocusedLabelColor = darkSecondary
val mfInputUnFocusedLabelColor = darkPrimaryVariant
val mfInputBottomColor = darkPrimary
val mfInputErrorColor = darkError

// MFProfilerScreen
val mfProfilerScreenSelectedCharacterColorBg = mfSplashScreenTitleColor

// MFFanInfoScreen
val mfFanInfoScreenTitleColor = mfSplashScreenSubTitleColor.copy()
val mfFanInfoScreenJerrys = darkSecondary.copy()

//MFHomeScreen
val mfHomeScreenLabelColor = mfSplashScreenTitleColor.copy()
val mfHomeScreenContenColor = darkPrimary.copy()

//MFSearchScreen
val mfSearchScreenDeadColor = darkError.copy()
val mfSearchScreenMaleColor = mortySecondColor.copy()
val mfSearchScreenFemaleColor = mortySecondColor.copy()

//MFCharacterScreen
val mfCharacterScreenCharTypeColor = mfSplashScreenTitleColor.copy()
val mfCharacterScreenFavouriteColor = darkError.copy()

//MFUserProfileScreen
val mfUserProfileScreenFavouriteColor = darkError.copy()


