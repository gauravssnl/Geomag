package com.sanmer.geomag.ui.navigation.normal

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sanmer.geomag.ui.navigation.MainScreen
import com.sanmer.geomag.ui.navigation.animated.HomeScreen
import com.sanmer.geomag.ui.screens.home.HomeScreen

fun NavGraphBuilder.homeScreen(
    navController: NavController
) = navigation(
    startDestination = HomeScreen.Home.route,
    route = MainScreen.Home.route
) {
    composable(
        route = HomeScreen.Home.route
    ) {
        HomeScreen(
            navController = navController
        )
    }
}