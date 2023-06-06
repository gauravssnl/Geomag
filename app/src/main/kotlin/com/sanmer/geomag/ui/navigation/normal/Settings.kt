package com.sanmer.geomag.ui.navigation.normal

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sanmer.geomag.ui.navigation.MainScreen
import com.sanmer.geomag.ui.navigation.animated.SettingsScreen
import com.sanmer.geomag.ui.screens.settings.SettingsScreen
import com.sanmer.geomag.ui.screens.settings.about.AboutScreen

fun NavGraphBuilder.settingsScreen(
    navController: NavController
) = navigation(
    startDestination = SettingsScreen.Home.route,
    route = MainScreen.Settings.route
) {
    composable(
        route = SettingsScreen.Home.route
    ) {
        SettingsScreen(
            navController = navController
        )
    }

    composable(
        route = SettingsScreen.About.route
    ) {
        AboutScreen(
            navController = navController
        )
    }
}