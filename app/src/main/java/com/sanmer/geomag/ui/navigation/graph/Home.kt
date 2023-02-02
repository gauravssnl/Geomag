package com.sanmer.geomag.ui.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.sanmer.geomag.ui.navigation.MainGraph
import com.sanmer.geomag.ui.page.regular.RegularScreen
import com.sanmer.geomag.ui.page.simple.SimpleScreen

sealed class HomeGraph(val route: String) {
    object Regular : HomeGraph("regular")
    object Simple : HomeGraph("simple")
}

fun NavGraphBuilder.homeGraph(
    navController: NavController,
    startDestination: String,
) = navigation(
    startDestination = startDestination,
    route = MainGraph.Home.route,
) {
    composable(
        route = HomeGraph.Regular.route,
    ) {
        RegularScreen(
            navController = navController
        )
    }

    composable(
        route = HomeGraph.Simple.route,
    ) {
        SimpleScreen(
            navController = navController
        )
    }
}