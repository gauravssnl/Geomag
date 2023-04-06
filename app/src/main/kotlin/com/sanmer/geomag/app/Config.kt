package com.sanmer.geomag.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.sanmer.geomag.core.models.Models
import com.sanmer.geomag.ui.theme.Colors
import com.sanmer.geomag.utils.preference.getValue
import com.sanmer.geomag.utils.preference.setValue

object Config {
    // MODEL
    var MODEL by mutableStateOf(Models.IGRF.id)

    // THEME_COLOR
    var THEME_COLOR by mutableStateOf(
        if (Const.atLeastS) {
            Colors.Dynamic.id
        } else {
            Colors.Sakura.id
        }
    )

    // DARK_MODE
    const val FOLLOW_SYSTEM = 0
    const val ALWAYS_OFF = 1
    const val ALWAYS_ON = 2
    var DARK_MODE by mutableStateOf(FOLLOW_SYSTEM)

    @Composable
    fun isDarkTheme() = when (DARK_MODE) {
        ALWAYS_ON -> true
        ALWAYS_OFF -> false
        else -> isSystemInDarkTheme()
    }

    //SIMPLE_MODE
    var SIMPLE_MODE by mutableStateOf(false)
}