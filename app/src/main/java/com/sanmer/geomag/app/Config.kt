package com.sanmer.geomag.app

import com.sanmer.geomag.BuildConfig
import com.sanmer.geomag.core.models.Models
import com.sanmer.geomag.ui.theme.Colors
import com.tencent.mmkv.MMKV

object Config {
    private var kv = MMKV.mmkvWithID("config")

    // MODEL
    private const val MODEL_KEY = "MODEL"
    var MODEL: String
        get() = kv.decodeString(MODEL_KEY, Models.MIGRF.key)!!
        set(value) { kv.encode(MODEL_KEY, value) }

    // PREFERENCE
    private const val FOLLOW_SYSTEM = 0
    const val ALWAYS_OFF = 1
    const val ALWAYS_ON = 2

    // THEME_COLOR
    private const val THEME_COLOR_KEY = "THEME_COLOR"
    var THEME_COLOR: Int
        get() = kv.decodeInt(
            THEME_COLOR_KEY,
            if (Const.atLeastS) Colors.Dynamic.id else Colors.Sakura.id
        )
        set(value) { kv.encode(THEME_COLOR_KEY, value) }

    // DARK_MODE
    private const val DARK_MODE_KEY = "DARK_MODE"
    var DARK_MODE: Int
        get() = kv.decodeInt(DARK_MODE_KEY, FOLLOW_SYSTEM)
        set(value) { kv.encode(DARK_MODE_KEY, value) }

    // ANALYTICS_COLLECTION
    private const val ANALYTICS_COLLECTION_KEY = "ANALYTICS_COLLECTION"
    var ANALYTICS_COLLECTION: Boolean
        get() = kv.decodeBool(ANALYTICS_COLLECTION_KEY, !BuildConfig.DEBUG)
        set(value) { kv.encode(ANALYTICS_COLLECTION_KEY, value) }
}