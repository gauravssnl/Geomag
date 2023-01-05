package com.sanmer.geomag.data.json

import com.sanmer.geomag.core.models.MagneticField
import com.sanmer.geomag.data.record.Record
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecordJson(
    val model: String,
    val time: String,
    val altitude: Double,
    val latitude: Double,
    val longitude: Double,
    @Json(name = "magnetic_field") val values: MagneticFieldJson
)

fun Record.toJson(): RecordJson {
    return RecordJson(
        model = model,
        time = time.toString(),
        altitude = location.altitude,
        latitude = location.latitude,
        longitude = location.longitude,
        values = values.toJson()
    )
}

@JsonClass(generateAdapter = true)
data class MagneticFieldJson(
    @Json(name = "declination") val declination: Double,
    @Json(name = "declination_sv") val declinationSV: Double,
    @Json(name = "inclination") val inclination: Double,
    @Json(name = "inclination_sv") val inclinationSV: Double,
    @Json(name = "horizontal_intensity") val horizontalIntensity: Double,
    @Json(name = "horizontal_sv") val horizontalSV: Double,
    @Json(name = "north_component") val northComponent: Double,
    @Json(name = "north_sv") val northSV: Double,
    @Json(name = "east_component") val eastComponent: Double,
    @Json(name = "east_sv") val eastSV: Double,
    @Json(name = "vertical_component") val verticalComponent: Double,
    @Json(name = "vertical_sv") val verticalSV: Double,
    @Json(name = "total_intensity") val totalIntensity: Double,
    @Json(name = "total_sv") val totalSV: Double,
)

fun MagneticField.toJson(): MagneticFieldJson {
    return MagneticFieldJson(
        declination, declinationSV,
        inclination, inclinationSV,
        horizontalIntensity, horizontalSV,
        northComponent, northSV,
        eastComponent, eastSV,
        verticalComponent, verticalSV,
        totalIntensity, totalSV
    )
}
