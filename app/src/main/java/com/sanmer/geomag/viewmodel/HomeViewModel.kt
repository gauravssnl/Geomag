package com.sanmer.geomag.viewmodel

import android.content.Context
import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanmer.geomag.app.Config
import com.sanmer.geomag.app.Const
import com.sanmer.geomag.core.localtion.AppLocationManager
import com.sanmer.geomag.core.models.*
import com.sanmer.geomag.core.time.DateTime
import com.sanmer.geomag.core.time.TimerManager
import com.sanmer.geomag.data.Constant
import com.sanmer.geomag.data.record.Record
import com.sanmer.geomag.data.record.toPosition
import com.sanmer.geomag.service.LocationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel : ViewModel() {
    val isTSRunning get() = TimerManager.isRunning
    private var _dateTime: DateTime by mutableStateOf(DateTime())
    val editDateTime: (DateTime) -> Unit = { _dateTime = it }
    val dateTime: DateTime
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
    private val location: Location? get() = _location
    val locationOrZero: Location
        get() = Location(location?.provider)
            .apply {
                altitude = (location?.altitude ?: 0.0) / 1000.0
                latitude = location?.latitude ?: 0.0
                longitude = location?.longitude ?: 0.0
            }

    private var _model: Models by mutableStateOf(getModels(Config.MODEL))
    val model: Models get() = _model

    fun changeLocationServiceState(context: Context) {
        if (!isLSRunning) {
            LocationService.start(context)
        } else {
            LocationService.stop(context)
            requestSingleUpdate()
        }
    }

    fun observeLocation(owner: LifecycleOwner) {
        LocationService.observeLocation(owner) {
            _location = it
        }
    }

    fun requestSingleUpdate() {
        _location = AppLocationManager.getLocation()
    }

    fun changeTimeServiceState() {
        if (!isTSRunning) {
            TimerManager.start()
        } else {
            TimerManager.stop()
            _dateTime = DateTime.now()
        }
    }

    fun updateModel(value: Models) {
        _model = value
        Config.MODEL = value.key
    }

    fun runModel(): Record {
        val date = decimalYears
        val location = locationOrZero
        return when (model) {
            is Models.MIGRF -> {
                IGRF.decimalYears = date
                IGRF.igrf(location.latitude, location.longitude, location.altitude)
                Record(
                    model = "IGRF",
                    time = dateTime,
                    location = location.toPosition(),
                    values = IGRF.MF
                )
            }
            is Models.MWMM -> {
                WMM.decimalYears = date
                WMM.wmm(location.latitude, location.longitude, location.altitude)
                Record(
                    model = "WMM",
                    time = dateTime,
                    location = location.toPosition(),
                    values = WMM.MF
                )
            }
        }
    }

    fun toDatabase(record: Record) {
        viewModelScope.launch(Dispatchers.IO) {
            Constant.insert(record)
        }
    }

    init {
        requestSingleUpdate()
        _dateTime = DateTime.now()
        Timber.d("HomeViewModel init")
    }

}