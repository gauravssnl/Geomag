package com.sanmer.geomag.ui.navigation.normal

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sanmer.geomag.ui.navigation.MainScreen
import com.sanmer.geomag.ui.navigation.animated.RecordsScreen
import com.sanmer.geomag.ui.screens.records.RecordsScreen
import com.sanmer.geomag.ui.screens.records.viewrecord.ViewRecordScreen

fun NavGraphBuilder.recordsScreen(
    navController: NavController
) = navigation(
    startDestination = RecordsScreen.Home.route,
    route = MainScreen.Records.route
) {
    composable(
        route = RecordsScreen.Home.route
    ) {
        RecordsScreen(
            navController = navController
        )
    }

    composable(
        route = RecordsScreen.View.route
    ) {
        ViewRecordScreen(
            navController = navController
        )
    }
}