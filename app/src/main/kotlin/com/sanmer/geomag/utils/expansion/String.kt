package com.sanmer.geomag.utils.expansion

fun String.toIntOr(v: Int): Int {
    if (isEmpty() || isBlank()) return v

    return try {
        toInt()
    } catch (e: NumberFormatException) {
        v
    }
}

fun String.toIntOrZero(): Int = toIntOr(0)

fun String.toDoubleOrZero(): Double {
    if (isEmpty() || isBlank()) return 0.0

    return try {
        toDouble()
    } catch (e: NumberFormatException) {
        0.0
    }
}