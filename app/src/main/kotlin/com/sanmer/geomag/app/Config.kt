package com.sanmer.geomag.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import com.sanmer.geomag.app.Config.getValue
import com.sanmer.geomag.app.Config.setValue
import com.sanmer.geomag.core.models.Models
import com.sanmer.geomag.ui.theme.Colors
import com.sanmer.geomag.utils.SPUtils
import kotlin.reflect.KProperty

object Config {
    private val sp = SPUtils

    // MODEL
    private const val MODEL_KEY = "MODEL"
    var MODEL: Int
        get() = sp.getValue(MODEL_KEY, Models.MIGRF.id)
        set(value) { sp.putValue(MODEL_KEY, value) }

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

    //SIMPLE_MODE
    var SIMPLE_MODE by mutableStateOf(false)

    operator fun <T> MutableState<T>.setValue(thisObj: Any?, property: KProperty<*>, value: T) {
        sp.putValue(property.name, value)
        this.value = update(value)
        this.value = value
    }

    operator fun <T> MutableState<T>.getValue(thisObj: Any?, property: KProperty<*>): T {
        return sp.getValue(property.name, value)
    }

    @Composable
    fun isDarkTheme() = when (DARK_MODE) {
        ALWAYS_ON -> true
        ALWAYS_OFF -> false
        else -> isSystemInDarkTheme()
    }

    private fun <T> update(value: T): T {
        val res: Any = when (value) {
            is Long -> Long.MAX_VALUE
            is String -> ""
            is Int -> Int.MAX_VALUE
            is Boolean -> !value
            is Float -> Float.MAX_VALUE
            else -> throw java.lang.IllegalArgumentException()
        }
        @Suppress("UNCHECKED_CAST")
        return res as T
    }
}