package com.sanmer.geomag.datastore

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.sanmer.geomag.Geomag
import com.sanmer.geomag.app.Const
import com.sanmer.geomag.ui.theme.Colors

data class UserData(
    val darkMode: DarkMode,
    val themeColor: Int,
    val fieldModel: Geomag.Models,
    val enableRecords: Boolean,
    val enableNavigationAnimation: Boolean
) {
    companion object {
        fun default() = UserData(
            darkMode = DarkMode.FOLLOW_SYSTEM,
            themeColor = if (Const.atLeastS) Colors.Dynamic.id else Colors.Sakura.id,
            fieldModel = Geomag.Models.IGRF,
            enableRecords = true,
            enableNavigationAnimation = false
        )
    }
}

@Composable
fun UserData.isDarkMode() = when (darkMode) {
    DarkMode.ALWAYS_OFF -> false
    DarkMode.ALWAYS_ON -> true
    else -> isSystemInDarkTheme()
}

fun UserData.toPreferences(): UserPreferences = UserPreferences.newBuilder()
    .setDarkMode(darkMode)
    .setThemeColor(themeColor)
    .setFieldModel(fieldModel.name)
    .setEnableRecords(enableRecords)
    .setEnableNavigationAnimation(enableNavigationAnimation)
    .build()

fun UserPreferences.toUserData() = UserData(
    darkMode = darkMode,
    themeColor = themeColor,
    fieldModel = Geomag.Models.valueOf(fieldModel),
    enableRecords = enableRecords,
    enableNavigationAnimation = enableNavigationAnimation
)