package com.sanmer.geomag.ui.screens.calculate

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.sanmer.geomag.R
import com.sanmer.geomag.ui.component.PageIndicator

@Composable
fun CalculateScreen(
    navController: NavController
) = PageIndicator(
    icon = R.drawable.hashtag_outline,
    text = "CalculateScreen"
)