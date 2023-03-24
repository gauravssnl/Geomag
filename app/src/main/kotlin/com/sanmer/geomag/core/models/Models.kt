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
    object IGRF : Models(
        id = 0,
        label = "IGRF",
        name = R.string.igrf,
        web = "https://www.ncei.noaa.gov/products/international-geomagnetic-reference-field",
        start = 1900.0,
        end = 2025.0
    )
    object WMM : Models(
        id = 1,
        label = "WMM",
        name = R.string.wmm,
        web = "https://www.ncei.noaa.gov/products/world-magnetic-model",
        start = 2020.0,
        end = 2025.0
    )
}

val models = listOf(
    Models.IGRF,
    Models.WMM
)

fun getModelById(id: Int) = models.find { it.id == id } ?: Models.IGRF

fun getModelByLabel(label: String) = models.find { it.label == label } ?: Models.IGRF