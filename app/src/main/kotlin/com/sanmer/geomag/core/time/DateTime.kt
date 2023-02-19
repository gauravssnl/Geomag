package com.sanmer.geomag.core.time

import android.icu.util.Calendar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTime(
    val year: Int = 1900,
    val month: Int = 1,
    val day: Int = 1,
    val hour: Int = 0,
    val minute: Int = 0,
    val second: Int = 0
) {
    companion object {
        fun now(): DateTime {
            val calendar = Calendar.getInstance()
            return now(calendar)
        }

        fun now(calendar: Calendar): DateTime {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            val second = calendar.get(Calendar.SECOND)

            return DateTime(
                year, month, day,
                hour, minute, second
            )
        }

        fun parse(text: String): DateTime {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val time = LocalDateTime.parse(text, formatter)
            return DateTime(
                year = time.year,
                month = time.monthValue,
                day = time.dayOfMonth,
                hour = time.hour,
                minute = time.minute,
                second = time.second
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is String -> other == toString()
            is DateTime -> this === other
            else -> false
        }
    }

    override fun hashCode(): Int {
        var result = year
        result = 31 * result + month
        result = 31 * result + day
        result = 31 * result + hour
        result = 31 * result + minute
        result = 31 * result + second
        return result
    }

    override fun toString(): String {
        return "${year}-${month.format}-${day.format} " +
                "${hour.format}:${minute.format}:${second.format}"
    }

    private val Int.format: String get() = "%02d".format(this)
}

fun String.toDateTime() = DateTime.parse(this)