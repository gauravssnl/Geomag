package com.sanmer.geomag.ui.navigation

import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sanmer.geomag.ui.expansion.navigatePopUpTo
import com.sanmer.geomag.ui.navigation.graph.HomeGraph
import com.sanmer.geomag.ui.navigation.graph.RecordGraph
import com.sanmer.geomag.ui.navigation.graph.SettingsGraph

private val mainGraph = listOf(
    MainGraph.Home,
    MainGraph.Records,
    MainGraph.Settings
)

private val homeGraph = listOf(
    HomeGraph.Regular.route
)

private val recordGraph = listOf(
    RecordGraph.Record.route,
    //RecordGraph.View.route
)

private val settingsGraph = listOf(
    SettingsGraph.Settings.route,
    SettingsGraph.AppTheme.route
)

@Composable
fun BottomNav(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        modifier = Modifier
            .imePadding()
    ) {
        mainGraph.forEach { screen ->
            val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
            val enable = when(screen) {
                is MainGraph.Home -> currentDestination?.route !in homeGraph
                is MainGraph.Records -> currentDestination?.route !in recordGraph
                is MainGraph.Settings -> currentDestination?.route !in settingsGraph
            }

            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = if (selected) {
                            screen.iconSelected
                        } else {
                            screen.icon
                        }),
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = screen.label),
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                alwaysShowLabel = true,
                selected = selected,
                onClick = {
                    if (enable) navController.navigatePopUpTo(screen.route)
                }
            )
        }
    }
}
