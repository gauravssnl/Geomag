package com.sanmer.geomag.core.models

import androidx.annotation.Keep

@Keep
object WMM {

    var decimalYears = 2020.00

    external fun wmm(
        latitude: Double, longitude: Double,
        alt_km :Double
    )

    val MF: MagneticField
        external get
}