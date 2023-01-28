package com.sanmer.geomag.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sanmer.geomag.core.models.Models
import com.sanmer.geomag.ui.theme.Colors
import com.sanmer.geomag.utils.SPUtils

object Config {
    private val sp = SPUtils

    // MODEL
    private const val MODEL_KEY = "MODEL"
    var MODEL: Int
        get() = sp.getValue(MODEL_KEY, Models.MIGRF.id)
        set(value) { sp.putValue(MODEL_KEY, value) }

    // PREFERENCE
    private const val FOLLOW_SYSTEM = 0
    const val ALWAYS_OFF = 1
    const val ALWAYS_ON = 2

    // THEME_COLOR
    private const val THEME_COLOR_KEY = "THEME_COLOR"
    var THEME_COLOR: Int
        get() = sp.getValue(
            THEME_COLOR_KEY,
            if (Const.atLeastS) Colors.Dynamic.id else Colors.Sakura.id
        )
        set(value) { sp.putValue(THEME_COLOR_KEY, value) }

    // DARK_MODE
    private const val DARK_MODE_KEY = "DARK_MODE"
    var DARK_MODE: Int
        get() = sp.getValue(DARK_MODE_KEY, FOLLOW_SYSTEM)
        set(value) { sp.putValue(DARK_MODE_KEY, value) }

    //SIMPLE_MODE
    private const val SIMPLE_MODE_KEY = "SIMPLE_MODE"
    var SIMPLE_MODE: Boolean
        get() = sp.getValue(SIMPLE_MODE_KEY, false)
        set(value) { sp.putValue(SIMPLE_MODE_KEY, value) }

    object State {
        private var THEME_COLOR by mutableStateOf(Config.THEME_COLOR)
        private var DARK_MODE by mutableStateOf(Config.DARK_MODE)
        private var SIMPLE_MODE by mutableStateOf(Config.SIMPLE_MODE)

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

        var simpleMode: Boolean
            get() = SIMPLE_MODE
            set(value) {
                SIMPLE_MODE = value
                Config.SIMPLE_MODE = value
            }

        @Composable
        fun isDarkTheme(): Boolean {
            return when (darkTheme) {
                ALWAYS_ON -> true
                ALWAYS_OFF -> false
                else -> isSystemInDarkTheme()
            }
        }
    }
}