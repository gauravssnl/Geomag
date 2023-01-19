package com.sanmer.geomag.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.os.Process
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ServiceCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.repeatOnLifecycle
import com.sanmer.geomag.R
import com.sanmer.geomag.app.Const
import com.sanmer.geomag.core.localtion.AppLocationManager
import com.sanmer.geomag.ui.activity.main.MainActivity
import com.sanmer.geomag.utils.NotificationUtils
import timber.log.Timber

class LocationService : LifecycleService() {
    private val listener = AppLocationListener()
    private val context = this

    override fun onCreate() {
        super.onCreate()
        Timber.d("onCreate")
        isRunning = true
        AppLocationManager.requestLocationUpdates(listener)
        setForeground()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null && ACTION_STOP_LISTEN == intent.action) {
            stopSelf()
            return START_NOT_STICKY
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")
        isRunning = false
        AppLocationManager.removeUpdates(listener)
        ServiceCompat.stopForeground(this, ServiceCompat.STOP_FOREGROUND_REMOVE)
    }

    private fun setForeground() {
        val intent = Intent(this, LocationService::class.java).apply {
            action = ACTION_STOP_LISTEN
        }
        val stopSelf = PendingIntent.getService(this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationUtils
            .buildNotification(context, Const.NOTIFICATION_ID_LOCATION)
            .setContentTitle(getString(R.string.notification_name_location))
            .setContentText(getString(R.string.message_location_click))
            .setContentIntent(NotificationUtils.getActivity(context, MainActivity::class))
            .addAction(0, getString(R.string.action_stop), stopSelf)
            .setOngoing(true)
            .build()
        startForeground(Process.myPid(), notification)
    }

    class AppLocationListener: LocationListener {
        override fun onLocationChanged(value: Location) {
            Timber.d("onLocationChanged")

            location = value
        }

        override fun onProviderEnabled(provider: String) {
            super.onProviderEnabled(provider)
            Timber.d("onProviderEnabled")
        }

        override fun onProviderDisabled(provider: String) {
            super.onProviderDisabled(provider)
            Timber.d("onProviderEnabled")
        }

        override fun onFlushComplete(requestCode: Int) {
            super.onFlushComplete(requestCode)
            Timber.d("onFlushComplete")
        }
    }

    companion object {
        const val ACTION_STOP_LISTEN = "ACTION_STOP_LISTEN"

        var isRunning by mutableStateOf(false)
            private set

        var location by mutableStateOf<Location?>(null)
            private set

        fun start(context: Context) {
            val intent = Intent(context, LocationService::class.java)
            context.startService(intent)
        }

        fun stop(context: Context) {
            val intent = Intent(context, LocationService::class.java)
            context.stopService(intent)
        }
    }
}