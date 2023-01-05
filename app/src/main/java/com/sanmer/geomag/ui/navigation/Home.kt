package com.sanmer.geomag.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.sanmer.geomag.ui.page.home.HomeScreen

sealed class HomeGraph(val route: String) {
    object Home : HomeGraph("home")
}

fun NavGraphBuilder.homeGraph(
    navController: NavController
) {
    navigation(
        startDestination = HomeGraph.Home.route,
        route = MainGraph.Home.route
    ) {
        composable(
            route = HomeGraph.Home.route,
        ) {
            HomeScreen()
        }
    }
}