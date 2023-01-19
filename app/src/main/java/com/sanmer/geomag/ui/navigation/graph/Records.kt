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
import com.sanmer.geomag.ui.page.records.RecordsScreen
import com.sanmer.geomag.ui.page.view.ViewScreen

sealed class RecordGraph(val route: String) {
    object Record : RecordGraph("record")
    object View : RecordGraph("view")
}

fun NavGraphBuilder.recordGraph(
    navController: NavController
) {
    navigation(
        startDestination = RecordGraph.Record.route,
        route = MainGraph.Records.route
    ) {
        composable(
            route = RecordGraph.Record.route,
        ) {
            RecordsScreen(
                navController = navController
            )
        }

        composable(
            route = "${RecordGraph.View.route}/{index}",
            arguments = listOf(navArgument("index") { type = NavType.IntType }),
            enterTransition = { SlideIn.rightToLeft },
            exitTransition = { SlideOut.leftToRight }
        ) { backStackEntry ->
            val index = backStackEntry.arguments?.getInt("index") ?: 0
            val record = Constant.records[index]

            ViewScreen(
                navController = navController,
                record = record
            )
        }
    }
}