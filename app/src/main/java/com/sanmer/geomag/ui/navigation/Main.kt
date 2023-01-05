package com.sanmer.geomag.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sanmer.geomag.R
import com.sanmer.geomag.ui.expansion.navigatePopUpTo
import timber.log.Timber

sealed class MainGraph(
    val route: String,
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
    @DrawableRes val iconSelected: Int
) {
    object Home : MainGraph(
        route = "homeGraph",
        label = R.string.page_home,
        icon = R.drawable.home_outline,
        iconSelected = R.drawable.home_bold
    )
    object Records : MainGraph(
        route = "recordsGraph",
        label = R.string.page_record,
        icon = R.drawable.message_text_outline,
        iconSelected = R.drawable.message_text_bold
    )
    object Settings : MainGraph(
        route = "SettingsGraph",
        label = R.string.page_settings,
        icon = R.drawable.setting_outline,
        iconSelected = R.drawable.setting_bold
    )
}

private val mainGraph = listOf(
    MainGraph.Home,
    MainGraph.Records,
    MainGraph.Settings
)

private val homeGraph = listOf(
    HomeGraph.Home.route
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
fun BottomNavigation(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
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
                    if (enable) {
                        navController.navigatePopUpTo(screen.route)
                    }
                }
            )
        }
    }
}
