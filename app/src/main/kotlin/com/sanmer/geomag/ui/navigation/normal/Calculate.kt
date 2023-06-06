package com.sanmer.geomag.ui.navigation.normal

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sanmer.geomag.ui.navigation.MainScreen
import com.sanmer.geomag.ui.navigation.animated.CalculateScreen
import com.sanmer.geomag.ui.screens.calculate.CalculateScreen

fun NavGraphBuilder.calculateScreen(
    navController: NavController
) = navigation(
    startDestination = CalculateScreen.Home.route,
    route = MainScreen.Calculate.route
) {
    composable(
        route = CalculateScreen.Home.route
    ) {
        CalculateScreen(
            navController = navController
        )
    }
}