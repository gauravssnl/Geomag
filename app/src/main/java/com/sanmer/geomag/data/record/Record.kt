package com.sanmer.geomag.data.record

import com.sanmer.geomag.core.models.MagneticField
import com.sanmer.geomag.core.time.DateTime

data class Record(
    val model: String,
    val time: DateTime,
    val location: Position,
    val values: MagneticField
)
