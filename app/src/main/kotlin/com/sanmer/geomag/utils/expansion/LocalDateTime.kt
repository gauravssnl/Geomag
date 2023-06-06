package com.sanmer.geomag.utils.expansion

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun LocalDateTime.Companion.now() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

fun LocalDateTime.copy(
    year: Int? = null,
    monthNumber: Int? = null,
    dayOfMonth: Int? = null,
    hour: Int? = null,
    minute: Int? = null,
    second: Int? = null,
    nanosecond: Int? = null
) = LocalDateTime(
    year = year ?: this.year,
    monthNumber = monthNumber ?: this.monthNumber,
    dayOfMonth = dayOfMonth ?: this.dayOfMonth,
    hour = hour ?: this.hour,
    minute = minute ?: this.minute,
    second = second ?: this.second,
    nanosecond = nanosecond ?: this.nanosecond
)