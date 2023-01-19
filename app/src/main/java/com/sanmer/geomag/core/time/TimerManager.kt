package com.sanmer.geomag.core.time

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import timber.log.Timber
import java.util.*
import kotlin.concurrent.timerTask

object TimerManager {
    var dateTime: DateTime by mutableStateOf(DateTime.now())
        private set
    var isRunning by mutableStateOf(false)
        private set

    private lateinit var timer: Timer
    private lateinit var timerTask: TimerTask

    fun init() = start()

    fun start() {
        Timber.i("TimerManager start")

        isRunning = true
        timer = Timer()
        timerTask = timerTask {
            dateTime = DateTime.now()
        }
        timer.schedule(
            timerTask,
            Date(), 1000
        )
    }

    fun stop() {
        Timber.i("TimerManager stop")

        if (isRunning) {
            isRunning = false
            timer.cancel()
        }
    }
}
