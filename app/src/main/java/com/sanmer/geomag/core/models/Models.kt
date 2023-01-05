package com.sanmer.geomag.core.models

import androidx.annotation.StringRes
import com.sanmer.geomag.R

sealed class Models(
    val key: String,
    val label: String,
    @StringRes val name: Int,
    val web: String,
    val start: Double,
    val end: Double,
    val enable: Boolean = false
) {
    object MIGRF : Models(
        key = "igrf",
        label = "IGRF",
        name = R.string.igrf,
        web = "https://doi.org/10.1186/s40623-020-01288-x",
        start = 1900.0,
        end = 2025.0,
        enable = true,
    )
    object MWMM : Models(
        key = "wmm",
        label = "WMM",
        name = R.string.wmm,
        web = "https://doi.org/10.25923/ytk1-yx35",
        start = 2020.0,
        end = 2025.0,
        enable = true
    )
}

val models = listOf(
    Models.MIGRF,
    Models.MWMM
)

fun getModels(key: String): Models {
    return models.find {
        it.key == key
    } ?: Models.MIGRF
}