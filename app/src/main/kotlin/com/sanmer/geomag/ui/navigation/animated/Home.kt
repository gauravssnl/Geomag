package com.sanmer.geomag.ui.navigation.animated

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.sanmer.geomag.ui.navigation.MainScreen
import com.sanmer.geomag.ui.screens.home.HomeScreen

enum class HomeScreen(val route: String) {
    Home("Home")
}

fun NavGraphBuilder.homeScreen(
    navController: NavController
) = navigation(
    startDestination = HomeScreen.Home.route,
    route = MainScreen.Home.route
) {
    composable(
        route = HomeScreen.Home.route,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() }
    ) {
        HomeScreen(
            navController = navController
        )
    }
}