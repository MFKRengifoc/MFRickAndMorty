package com.manoffocus.mfrickandmorty.navigation

enum class MFScreens {
    MFSplashScreen,
    MFProfilerScreen,
    MFFanInfoScreen,
    MFHomeScreen,
    MFLocationScreen,
    MFUserProfileScreen,
    MFSeasonScreen,
    MFCharacterScreen,
    MFSearchScreen,
    MFQuizScreen;
    companion object {
        fun fromRoute(route: String): MFScreens
                = when(route.substringBefore("/")){
            MFSplashScreen.name ->  MFSplashScreen
            MFProfilerScreen.name ->  MFProfilerScreen
            MFFanInfoScreen.name ->  MFFanInfoScreen
            MFLocationScreen.name ->  MFLocationScreen
            MFUserProfileScreen.name ->  MFUserProfileScreen
            MFSeasonScreen.name ->  MFSeasonScreen
            MFCharacterScreen.name ->  MFCharacterScreen
            MFSearchScreen.name ->  MFSearchScreen
            MFQuizScreen.name ->  MFQuizScreen
            else -> MFHomeScreen
        }
    }
}