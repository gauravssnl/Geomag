package com.sanmer.geomag.ui.activity.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.sanmer.geomag.app.Config
import com.sanmer.geomag.ui.navigation.BottomNav
import com.sanmer.geomag.ui.navigation.MainGraph
import com.sanmer.geomag.ui.navigation.graph.HomeGraph
import com.sanmer.geomag.ui.navigation.graph.homeGraph
import com.sanmer.geomag.ui.navigation.graph.recordGraph
import com.sanmer.geomag.ui.navigation.graph.settingsGraph

private fun Boolean.toInt() = if (this) 1 else 0
private fun Int.toBoolean() = when (this) {
    1 -> true
    else -> false
}

@Composable
fun MainScreen() {
    val state = rememberPagerState(initialPage = Config.SIMPLE_MODE.toInt())

    LaunchedEffect(Config.SIMPLE_MODE) {
        val id = Config.SIMPLE_MODE.toInt()
        state.animateScrollToPage(id)
    }

    VerticalPager(
        pageCount = 2,
        state = state,
        flingBehavior = PagerDefaults.flingBehavior(
            state = state,
            pagerSnapDistance = PagerSnapDistance.atMost(0)
        ),
        userScrollEnabled = false
    ) {
        when {
            it.toBoolean() -> SimpleScreen()
            else -> RegularScreen()
        }
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