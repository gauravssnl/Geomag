package com.sanmer.geomag.data.record

import android.location.Location

class Position(
    val altitude: Double,
    val latitude: Double,
    val longitude: Double
) {
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Location -> {
                altitude == other.altitude
                && latitude == other.latitude
                && longitude == other.longitude
            }
            is Position -> {
                altitude == other.altitude
                && latitude == other.latitude
                && longitude == other.longitude
            }
            else -> false
        }
    }

    override fun hashCode(): Int {
        var result = altitude.hashCode()
        result = 31 * result + latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        return result
    }

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