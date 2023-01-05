package com.sanmer.geomag.core.models

import androidx.annotation.Keep

@Keep
object IGRF {

    var decimalYears = 1900.00

    external fun setDateTime(
        year: Int, month: Int, day: Int,
        hour: Int, min: Int, sec: Int
    )

    external fun igrf(
        latitude: Double, longitude: Double,
        alt_km :Double
    )

    val MF: MagneticField
        external get
}