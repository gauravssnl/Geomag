package com.sanmer.geomag.viewmodel

import android.content.Context
import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanmer.geomag.app.Config
import com.sanmer.geomag.core.AppLocationManager
import com.sanmer.geomag.core.models.*
import com.sanmer.geomag.core.TimerManager
import com.sanmer.geomag.data.Constant
import com.sanmer.geomag.data.record.Record
import com.sanmer.geomag.data.record.toPosition
import com.sanmer.geomag.service.LocationService
import com.sanmer.geomag.utils.expansion.nowTarget
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import timber.log.Timber

class HomeViewModel : ViewModel() {
    val isTSRunning get() = TimerManager.isRunning
    private var _dateTime: LocalDateTime by mutableStateOf(LocalDateTime.nowTarget())
    val editDateTime: (LocalDateTime) -> Unit = { _dateTime = it }
    val dateTime: LocalDateTime
        get() = if (isTSRunning) {
            TimerManager.dateTime
        } else {
            _dateTime
        }
    val decimalYears: Double
        get() = Geomag.toDecimalYears(dateTime)

    val isLSRunning get() = LocationService.isRunning
    private var _location: Location? by mutableStateOf(null)
    val editLocation: (Location) -> Unit = { _location = it }
    private val location: Location?
        get() = if (isLSRunning) {
            LocationService.location
        } else {
            _location
        }
    val locationOrZero: Location
        get() = Location(location?.provider)
            .apply {
                altitude = (location?.altitude ?: 0.0) / 1000.0
                latitude = location?.latitude ?: 0.0
                longitude = location?.longitude ?: 0.0
            }

    val model: Models get() = getModelById(Config.MODEL)
    var record: Record? by mutableStateOf(null)
        private set

    init {
        Timber.d("HomeViewModel init")

        snapshotFlow { AppLocationManager.isReady }
            .onEach { if (it) getLastKnownLocation() }
            .launchIn(viewModelScope)
    }

    fun toggleLocation(context: Context) {
        if (!isLSRunning) {
            LocationService.start(context)
        } else {
            LocationService.stop(context)
            getLastKnownLocation()
        }
    }

    fun getLastKnownLocation() {
        _location = AppLocationManager.getLastKnownLocation()
    }

    fun toggleTime() {
        if (!isTSRunning) {
            TimerManager.start()
        } else {
            TimerManager.stop()
            _dateTime = LocalDateTime.nowTarget()
        }
    }

    fun runModel() {
        Geomag.decimalYears = decimalYears

        val location = locationOrZero
        val values = Geomag.cal(location.latitude, location.longitude, location.altitude)

        record = Record(
            model = model.label,
            time = dateTime,
            location = location.toPosition(),
            values = values
        )

        viewModelScope.launch(Dispatchers.IO) {
            Constant.insert(record!!)
        }
    }
}