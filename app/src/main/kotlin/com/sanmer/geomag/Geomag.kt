package com.sanmer.geomag

import androidx.annotation.Keep
import com.sanmer.geomag.model.MagneticField
import kotlinx.datetime.LocalDateTime

object Geomag {
    @Keep
    var decimalYears = 2020.0

    init {
        System.loadLibrary("geomag")
    }

    private external fun toDecimalYears(
        year: Int, month: Int, day: Int,
        hour: Int, min: Int, sec: Int
    ) : Double

    fun toDecimalYears(dateTime: LocalDateTime) = toDecimalYears(
        dateTime.year, dateTime.monthNumber, dateTime.dayOfMonth,
        dateTime.hour, dateTime.minute, dateTime.second
    )

    external fun igrf(
        latitude: Double, longitude: Double,
        altKm :Double
    ): MagneticField

    external fun wmm(
        latitude: Double, longitude: Double, altKm :Double
    ): MagneticField

    enum class Models {
        IGRF,
        WMM
    }
}