package com.sanmer.geomag.app

import com.sanmer.geomag.BuildConfig
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
}