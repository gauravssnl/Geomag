package com.sanmer.geomag.utils

import timber.log.Timber

fun String.toIntOrZero(): Int {
    if (isEmpty() || isBlank()) return 0

    return try {
        toInt()
    } catch (e: NumberFormatException) {
        Timber.e(e.message)
        0
    }
}

fun String.toIntOr(v: Int): Int {
    if (isEmpty() || isBlank()) return v

    return try {
        toInt()
    } catch (e: NumberFormatException) {
        Timber.e(e.message)
        v
    }
}

fun String.toDoubleOrZero(): Double {
    if (isEmpty() || isBlank()) return 0.0

    return try {
        toDouble()
    } catch (e: NumberFormatException) {
        Timber.e(e.message)
        0.0
    }
}