package com.sanmer.geomag.data.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sanmer.geomag.core.models.Geomag
import com.sanmer.geomag.core.models.MagneticField
import com.sanmer.geomag.core.models.getModelByLabel
import com.sanmer.geomag.data.record.Position
import com.sanmer.geomag.data.record.Record
import kotlinx.datetime.toLocalDateTime

@Entity(tableName = "records")
data class RecordEntity(
    val model: String,
    val time: String,
    val altitude: Double,
    val latitude: Double,
    val longitude: Double,
    @Embedded val values: MagneticFieldEntity,
    @PrimaryKey val id: Double,
)

private val Record.primaryKey: Double get() {
    val decimal = Geomag.toDecimalYears(time)
    val position = location.altitude - location.latitude - location.longitude
    return decimal + position + getModelByLabel(model).id
}

fun Record.toEntity() = RecordEntity(
    model = model,
    time = time.toString(),
    altitude = location.altitude,
    latitude = location.latitude,
    longitude = location.longitude,
    values = values.toEntity(),
    id = primaryKey
)

fun RecordEntity.toRecord() = Record(
    model = model,
    time = time.toLocalDateTime(),
    location = Position(altitude, latitude, longitude),
    values = values.toMF()
)

@Entity(tableName = "magnetic_field")
data class MagneticFieldEntity(
    @ColumnInfo(name = "declination") val declination: Double,
    @ColumnInfo(name = "declination_sv") val declinationSV: Double,
    @ColumnInfo(name = "inclination") val inclination: Double,
    @ColumnInfo(name = "inclination_sv") val inclinationSV: Double,
    @ColumnInfo(name = "horizontal_intensity") val horizontalIntensity: Double,
    @ColumnInfo(name = "horizontal_sv") val horizontalSV: Double,
    @ColumnInfo(name = "north_component") val northComponent: Double,
    @ColumnInfo(name = "north_sv") val northSV: Double,
    @ColumnInfo(name = "east_component") val eastComponent: Double,
    @ColumnInfo(name = "east_sv") val eastSV: Double,
    @ColumnInfo(name = "vertical_component") val verticalComponent: Double,
    @ColumnInfo(name = "vertical_sv") val verticalSV: Double,
    @ColumnInfo(name = "total_intensity") val totalIntensity: Double,
    @ColumnInfo(name = "total_sv") val totalSV: Double
)

fun MagneticField.toEntity() = MagneticFieldEntity(
    declination, declinationSV,
    inclination, inclinationSV,
    horizontalIntensity, horizontalSV,
    northComponent, northSV,
    eastComponent, eastSV,
    verticalComponent, verticalSV,
    totalIntensity, totalSV
)

fun MagneticFieldEntity.toMF() = MagneticField(
    declination, declinationSV,
    inclination, inclinationSV,
    horizontalIntensity, horizontalSV,
    northComponent, northSV,
    eastComponent, eastSV,
    verticalComponent, verticalSV,
    totalIntensity, totalSV
)
