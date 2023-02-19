package com.sanmer.geomag.core.localtion

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import timber.log.Timber

object AppLocationManager {
    private lateinit var permissionsState: MultiplePermissionsState
    lateinit var locationManager: LocationManager

    var isReady by mutableStateOf(false)
        private set
    val isEnable: Boolean
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            locationManager.isLocationEnabled
        } else {
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }

    private val criteria: Criteria
        get() = Criteria().apply {
            accuracy = Criteria.ACCURACY_FINE
            isAltitudeRequired = true
            isCostAllowed = true
        }

    private val bestProvider: String
        get() = locationManager.getBestProvider(
            criteria, true
        ) ?: LocationManager.GPS_PROVIDER

    @Composable
    fun PermissionsState(
        onGranted: () -> Unit = {},
        onDenied: () -> Unit = {},
        onInit: () -> Unit = {},
    ) {
        val locationPermissionsState = rememberMultiplePermissionsState(
            listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )
        permissionsState = locationPermissionsState

        val allPermissionsRevoked =
            locationPermissionsState.permissions.size ==
                    locationPermissionsState.revokedPermissions.size

        if (!allPermissionsRevoked) {
            onGranted()
            isReady = true
        } else if (locationPermissionsState.shouldShowRationale) {
            onDenied()
        } else {
            onInit()
        }
    }

    fun launchPermissionRequest() {
        permissionsState.launchMultiplePermissionRequest()
    }

    fun init(context: Context): LocationManager {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        Timber.d("isLocationEnabled: $isEnable")
        return locationManager
    }

    @SuppressLint("MissingPermission")
    fun requestLocationUpdates(listener: LocationListener) {
        Timber.i("AppLocationManager: requestLocationUpdates")
        bestProvider.let {
            locationManager.requestLocationUpdates(
                it, 1000, 1f,
                listener
            )
        }
    }

    fun removeUpdates(listener: LocationListener) {
        Timber.i("AppLocationManager: removeUpdates")
        locationManager.removeUpdates(listener)
    }

    @SuppressLint("MissingPermission")
    fun getLocation(): Location? {
        Timber.d("AppLocationManager: getLastKnownLocation")
        return locationManager.getLastKnownLocation(bestProvider)
    }

}