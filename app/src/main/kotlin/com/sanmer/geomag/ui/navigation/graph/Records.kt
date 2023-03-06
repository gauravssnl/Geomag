package com.sanmer.geomag.ui.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.sanmer.geomag.data.Constant
import com.sanmer.geomag.ui.animate.SlideIn
import com.sanmer.geomag.ui.animate.SlideOut
import com.sanmer.geomag.ui.navigation.MainGraph
import com.sanmer.geomag.ui.screens.records.RecordsScreen
import com.sanmer.geomag.ui.screens.viewrecord.ViewRecordScreen

sealed class RecordGraph(val route: String) {
    object Record : RecordGraph("record")
    object View : RecordGraph("view") {
        val way: String = "${route}/{index}"
        fun Int.toRoute() = "${route}/${this}"
    }
}

fun NavGraphBuilder.recordGraph(
    navController: NavController
) = navigation(
    startDestination = RecordGraph.Record.route,
    route = MainGraph.Records.route
) {
    composable(
        route = RecordGraph.Record.route,
        enterTransition = {
            when (initialState.destination.route) {
                RecordGraph.View.way -> SlideIn.leftToRight
                else -> null
            }
        },
        exitTransition = {
            when (initialState.destination.route) {
                RecordGraph.View.way -> SlideOut.rightToLeft
                else -> null
            }
        }
    ) {
        RecordsScreen(
            navController = navController
        )
    }

    composable(
        route = RecordGraph.View.way,
        arguments = listOf(navArgument("index") { type = NavType.IntType }),
        enterTransition = { SlideIn.rightToLeft },
        exitTransition = { SlideOut.leftToRight }
    ) {
        ViewRecordScreen(
            navController = navController
        )
    }
}