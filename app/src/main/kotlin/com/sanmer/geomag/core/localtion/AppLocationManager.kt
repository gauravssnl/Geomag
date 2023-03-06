package com.sanmer.geomag.core.localtion

import android.Manifest.permission
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationListenerCompat
import androidx.core.location.LocationManagerCompat
import androidx.core.location.LocationRequestCompat
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.sanmer.geomag.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.shareIn
import timber.log.Timber

object AppLocationManager {
    private lateinit var permissionsState: MultiplePermissionsState

    private val context by lazy { App.context }
    private val locationManager by lazy { context.getSystemService(Context.LOCATION_SERVICE) as LocationManager }

    var isReady by mutableStateOf(false)
        private set
    val isEnable: Boolean get() = LocationManagerCompat.isLocationEnabled(locationManager)

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
        permissionsState = rememberMultiplePermissionsState(
            listOf(
                permission.ACCESS_FINE_LOCATION,
                permission.ACCESS_COARSE_LOCATION,
            )
        )

        val allPermissionsRevoked =
            permissionsState.permissions.size ==
                    permissionsState.revokedPermissions.size

        if (!allPermissionsRevoked) {
            onGranted()
            isReady = true
        } else if (permissionsState.shouldShowRationale) {
            onDenied()
        } else {
            onInit()
        }
    }

    fun launchPermissionRequest() {
        permissionsState.launchMultiplePermissionRequest()
    }

    fun init() {
        Timber.d("isLocationEnabled: $isEnable")
    }

    fun getLastKnownLocation(): Location? {
        if (ActivityCompat.checkSelfPermission(
                context, permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context, permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return null
        }

        Timber.d("AppLocationManager: getLastKnownLocation")
        return locationManager.getLastKnownLocation(bestProvider)
    }

    @RequiresPermission(anyOf = [permission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION])
    private fun requestLocationUpdates(listener: LocationListenerCompat) {
        Timber.i("AppLocationManager: requestLocationUpdates")
        val localeRequestCompat = LocationRequestCompat.Builder(1)
            .setQuality(LocationRequestCompat.QUALITY_HIGH_ACCURACY)
            .setMinUpdateDistanceMeters(0f)
            .build()

        LocationManagerCompat.requestLocationUpdates(
            locationManager,
            bestProvider,
            localeRequestCompat,
            listener,
            Looper.getMainLooper()
        )
    }

    fun locationUpdates(scope: CoroutineScope) = callbackFlow {
        if (ActivityCompat.checkSelfPermission(
                context, permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context, permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            close()
        }

        val callback = LocationListenerCompat {
            trySend(it)
        }

        try {
            requestLocationUpdates(callback)
        } catch (e: Exception) {
            Timber.e(e.message)
            close(e)
        }

        awaitClose {
            Timber.i("AppLocationManager: removeUpdates")
            LocationManagerCompat.removeUpdates(locationManager, callback)
        }
    }.shareIn(
        scope,
        replay = 0,
        started = SharingStarted.WhileSubscribed()
    )
}