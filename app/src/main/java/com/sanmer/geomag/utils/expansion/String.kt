package com.sanmer.geomag.utils.expansion

import timber.log.Timber

fun String.toIntOr(v: Int): Int {
    if (isEmpty() || isBlank()) return v

    return try {
        toInt()
    } catch (e: NumberFormatException) {
        Timber.e(e.message)
        v
    }
}

fun String.toIntOrZero(): Int = toIntOr(0)

fun String.toDoubleOrZero(): Double {
    if (isEmpty() || isBlank()) return 0.0

    return try {
        toDouble()
    } catch (e: NumberFormatException) {
        Timber.e(e.message)
        0.0
    }
}