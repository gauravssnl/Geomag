package com.sanmer.geomag.core.models

import androidx.annotation.Keep
import com.sanmer.geomag.app.Config
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
        alt_km :Double
    ): MagneticField

    external fun wmm(
        latitude: Double, longitude: Double, alt_km :Double
    ): MagneticField

    fun cal(
        latitude: Double, longitude: Double,
        alt_km :Double
    ) = when (Config.MODEL) {
        Models.IGRF.id -> igrf(latitude, longitude, alt_km)
        Models.WMM.id -> wmm(latitude, longitude, alt_km)
        else -> igrf(latitude, longitude, alt_km)
    }
}