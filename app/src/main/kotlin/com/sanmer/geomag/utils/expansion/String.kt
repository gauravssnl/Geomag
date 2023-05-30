package com.sanmer.geomag.utils.expansion

fun String.toDoubleOrZero(): Double {
    if (isEmpty() || isBlank()) return 0.0

    return try {
        toDouble()
    } catch (e: NumberFormatException) {
        0.0
    }
}