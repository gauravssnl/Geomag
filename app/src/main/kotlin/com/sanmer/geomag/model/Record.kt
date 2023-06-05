package com.sanmer.geomag.model

import com.sanmer.geomag.Geomag
import com.sanmer.geomag.utils.expansion.now
import com.squareup.moshi.JsonClass
import kotlinx.datetime.LocalDateTime

@JsonClass(generateAdapter = true)
data class Record(
    val model: Geomag.Models,
    val time: LocalDateTime,
    val position: Position,
    val values: MagneticField
) {
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Record -> {
                model == other.model
                && time == other.time
                && position == other.position
            }
            else -> false
        }
    }

    override fun hashCode(): Int {
        var result = model.hashCode()
        result = 31 * result + time.hashCode()
        result = 31 * result + position.hashCode()
        result = 31 * result + values.hashCode()
        return result
    }

    companion object {
        fun empty() = Record(
            model = Geomag.Models.IGRF,
            time = LocalDateTime.now(),
            position = Position.empty(),
            values = MagneticField.empty()
        )
    }
}
