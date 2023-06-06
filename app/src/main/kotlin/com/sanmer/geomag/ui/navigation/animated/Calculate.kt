package com.sanmer.geomag.ui.navigation.animated

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.sanmer.geomag.ui.animate.slideInRightToLeft
import com.sanmer.geomag.ui.animate.slideOutLeftToRight
import com.sanmer.geomag.ui.navigation.MainScreen
import com.sanmer.geomag.ui.screens.calculate.CalculateScreen

enum class CalculateScreen(val route: String) {
    Home("Calculate")
}

fun NavGraphBuilder.calculateScreen(
    navController: NavController
) = navigation(
    startDestination = CalculateScreen.Home.route,
    route = MainScreen.Calculate.route
) {
    composable(
        route = CalculateScreen.Home.route,
        enterTransition = { slideInRightToLeft() + fadeIn() },
        exitTransition = { slideOutLeftToRight() + fadeOut() }
    ) {
        CalculateScreen(
            navController = navController
        )
    }
}