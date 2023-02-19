package com.sanmer.geomag.core.models

import androidx.annotation.StringRes
import com.sanmer.geomag.R

sealed class Models(
    val id: Int,
    val label: String,
    @StringRes val name: Int,
    val web: String,
    val start: Double,
    val end: Double,
    val enable: Boolean = true
) {
    object MIGRF : Models(
        id = 0,
        label = "IGRF",
        name = R.string.igrf,
        web = "https://www.ncei.noaa.gov/products/international-geomagnetic-reference-field",
        start = 1900.0,
        end = 2025.0
    )
    object MWMM : Models(
        id = 1,
        label = "WMM",
        name = R.string.wmm,
        web = "https://www.ncei.noaa.gov/products/world-magnetic-model",
        start = 2020.0,
        end = 2025.0
    )
}

val models = listOf(
    Models.MIGRF,
    Models.MWMM
)

fun getModel(id: Int): Models {
    return models.find {
        it.id == id
    } ?: Models.MIGRF
}

fun getModelID(label: String): Int {
    return models.find {
        it.label == label
    }?.id ?: Models.MIGRF.id
}