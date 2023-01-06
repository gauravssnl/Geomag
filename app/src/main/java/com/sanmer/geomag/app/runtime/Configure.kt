package com.sanmer.geomag.app.runtime

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sanmer.geomag.app.Config

object Configure {
    private var THEME_COLOR by mutableStateOf(Config.THEME_COLOR)
    private var DARK_MODE by mutableStateOf(Config.DARK_MODE)

    var themeColor: Int
        get() = THEME_COLOR
        set(value) {
            THEME_COLOR = value
            Config.THEME_COLOR = value
        }

    var darkTheme: Int
        get() = DARK_MODE
        set(value) {
            DARK_MODE = value
            Config.DARK_MODE = value
        }

    @Composable
    fun isDarkTheme(): Boolean {
        return when (darkTheme) {
            Config.ALWAYS_ON -> true
            Config.ALWAYS_OFF -> false
            else -> isSystemInDarkTheme()
        }
    }
}
