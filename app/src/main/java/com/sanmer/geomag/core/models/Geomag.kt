package com.sanmer.geomag.core.models

import androidx.annotation.Keep
import com.sanmer.geomag.core.time.DateTime
import timber.log.Timber

@Keep
object Geomag {

    fun init() {
        Timber.d("Geomag init")
        System.loadLibrary("geomag")
    }

    private external fun toDecimalYears(
        year: Int, month: Int, day: Int,
        hour: Int, min: Int, sec: Int
    ) : Double

    fun toDecimalYears(dateTime: DateTime): Double {
        return toDecimalYears(
            dateTime.year, dateTime.month, dateTime.day,
            dateTime.hour, dateTime.minute, dateTime.second
        )
    }
}