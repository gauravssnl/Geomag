package com.sanmer.geomag.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavController
import com.sanmer.geomag.R
import com.sanmer.geomag.utils.expansion.navigatePopUpTo

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

fun NavController.navigateToHome() = navigatePopUpTo(MainGraph.Home.route)
fun NavController.navigateToRecords() = navigatePopUpTo(MainGraph.Records.route)
fun NavController.navigateToSettings() = navigatePopUpTo(MainGraph.Settings.route)