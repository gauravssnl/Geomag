package com.sanmer.geomag.ui.activity.main

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.sanmer.geomag.ui.navigation.MainScreen
import com.sanmer.geomag.ui.navigation.animated.calculateScreen as animatedCalculateScreen
import com.sanmer.geomag.ui.navigation.animated.homeScreen as animatedHomeScreen
import com.sanmer.geomag.ui.navigation.animated.recordsScreen as animatedRecordsScreen
import com.sanmer.geomag.ui.navigation.animated.settingsScreen as animatedSettingsScreen
import com.sanmer.geomag.ui.navigation.normal.calculateScreen as normalCalculateScreen
import com.sanmer.geomag.ui.navigation.normal.homeScreen as normalHomeScreen
import com.sanmer.geomag.ui.navigation.normal.recordsScreen as normalRecordsScreen
import com.sanmer.geomag.ui.navigation.normal.settingsScreen as normalSettingsScreen

@Composable
fun AnimatedMainScreen() {
    val navController = rememberAnimatedNavController()

    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        AnimatedNavHost(
            navController = navController,
            startDestination = MainScreen.Home.route
        ) {
            animatedHomeScreen(
                navController = navController
            )
            animatedCalculateScreen(
                navController = navController
            )
            animatedRecordsScreen(
                navController = navController
            )
            animatedSettingsScreen(
                navController = navController
            )
        }
    }
}

@Composable
fun NormalMainScreen() {
    val navController = rememberNavController()

    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = MainScreen.Home.route
        ) {
            normalHomeScreen(
                navController = navController
            )
            normalCalculateScreen(
                navController = navController
            )
            normalRecordsScreen(
                navController = navController
            )
            normalSettingsScreen(
                navController = navController
            )
        }
    }
}