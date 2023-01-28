package com.sanmer.geomag.ui.activity.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.sanmer.geomag.app.Config.State
import com.sanmer.geomag.ui.animate.SlideIn
import com.sanmer.geomag.ui.animate.SlideOut
import com.sanmer.geomag.ui.navigation.BottomNav
import com.sanmer.geomag.ui.navigation.MainGraph
import com.sanmer.geomag.ui.navigation.graph.HomeGraph
import com.sanmer.geomag.ui.navigation.graph.homeGraph
import com.sanmer.geomag.ui.navigation.graph.recordGraph
import com.sanmer.geomag.ui.navigation.graph.settingsGraph

@Composable
fun MainScreen() {
    AnimatedVisibility(
        visible = !State.simpleMode,
        enter = SlideIn.topToBottom,
        exit = SlideOut.bottomToTop
    ) {
        RegularScreen()
    }

    AnimatedVisibility(
        visible = State.simpleMode,
        enter = SlideIn.bottomToTop,
        exit = SlideOut.topToBottom
    ) {
        SimpleScreen()
    }
}

@Composable
private fun RegularScreen() {
    val navController = rememberAnimatedNavController()

    Scaffold(
        bottomBar = {
            BottomNav(
                navController = navController
            )
        },
    ) {
        AnimatedNavHost(
            modifier = Modifier
                .padding(bottom = it.calculateBottomPadding()),
            navController = navController,
            startDestination = MainGraph.Home.route,
            enterTransition = { fadeIn(animationSpec = tween(400)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            homeGraph(
                navController = navController,
                startDestination = HomeGraph.Regular.route
            )
            recordGraph(
                navController = navController
            )
            settingsGraph(
                navController = navController
            )
        }
    }
}

@Composable
private fun SimpleScreen() {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = MainGraph.Home.route,
        enterTransition = { fadeIn(animationSpec = tween(400)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {
        homeGraph(
            navController = navController,
            startDestination = HomeGraph.Simple.route
        )
        recordGraph(
            navController = navController
        )
        settingsGraph(
            navController = navController
        )
    }
}