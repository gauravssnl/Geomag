package com.sanmer.geomag.app

import android.os.Build

object Const {
    // DEVICE
    val atLeastS = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    // NOTIFICATION
    const val CHANNEL_ID_LOCATION = "location_service"
    const val NOTIFICATION_ID_LOCATION = 1024
}