package com.sanmer.geomag.data.record

import com.sanmer.geomag.core.models.MagneticField
import com.sanmer.geomag.core.time.DateTime
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Record(
    val model: String,
    val time: DateTime,
    val location: Position,
    val values: MagneticField
) {
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Record -> {
                model == other.model
                && time.toString() == other.time.toString()
                && location == other.location
            }
            else -> false
        }
    }

    override fun hashCode(): Int {
        var result = model.hashCode()
        result = 31 * result + time.hashCode()
        result = 31 * result + location.hashCode()
        result = 31 * result + values.hashCode()
        return result
    }
}
