package com.sanmer.geomag

import androidx.annotation.Keep
import com.sanmer.geomag.model.MagneticField
import com.sanmer.geomag.model.Position
import com.sanmer.geomag.model.Record
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
        latitude: Double, longitude: Double, altKm :Double
    ): MagneticField

    external fun wmm(
        latitude: Double, longitude: Double, altKm :Double
    ): MagneticField

    fun igrf(
        dataTime: LocalDateTime,
        position: Position
    ): MagneticField {
        decimalYears = toDecimalYears(dataTime)
        return igrf(position.latitude, position.longitude, position.altitude)
    }

    fun wmm(
        dataTime: LocalDateTime,
        position: Position
    ): MagneticField {
        decimalYears = toDecimalYears(dataTime)
        return wmm(position.latitude, position.longitude, position.altitude)
    }

    suspend fun run(
        model: Models,
        dataTime: LocalDateTime,
        position: Position
    ): Record = withContext(Dispatchers.Default) {
        val cal: (LocalDateTime, Position) -> MagneticField = when (model) {
            Models.IGRF -> ::igrf
            Models.WMM -> ::wmm
        }

        return@withContext Record(
            model = model,
            time = dataTime,
            position = position,
            values = cal(dataTime, position)
        )
    }

    enum class Models {
        IGRF,
        WMM
    }
}