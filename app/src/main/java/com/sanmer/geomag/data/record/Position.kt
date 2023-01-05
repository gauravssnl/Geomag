package com.sanmer.geomag.data.record

import android.location.Location

class Position(
    val altitude: Double,
    val latitude: Double,
    val longitude: Double
) {
    override fun toString(): String {
        return "${latitude}º N ${longitude}º W $altitude km"
    }
}

fun Location.toPosition(): Position {
    return Position(
        altitude = altitude,
        latitude = latitude,
        longitude = longitude
    )
}