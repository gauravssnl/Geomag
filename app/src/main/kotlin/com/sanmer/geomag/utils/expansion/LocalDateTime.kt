package com.sanmer.geomag.utils.expansion

import kotlinx.datetime.*

fun LocalDateTime.Companion.now() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

fun LocalDateTime.toTarget() = LocalDateTime(year, month, dayOfMonth, hour, minute, second)

fun LocalDateTime.Companion.nowTarget() = now().toTarget()