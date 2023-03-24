package com.sanmer.geomag

import android.app.Application
import com.sanmer.geomag.core.AppLocationManager
import com.sanmer.geomag.core.TimerManager
import com.sanmer.geomag.data.Constant
import com.sanmer.geomag.utils.NotificationUtils
import com.sanmer.geomag.utils.timber.DebugTree
import com.sanmer.geomag.utils.timber.ReleaseTree
import timber.log.Timber

class App : Application() {
    init {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }
    override fun onCreate() {
        super.onCreate()
        app = this

        NotificationUtils.init(this)

        AppLocationManager.init()
        TimerManager.init()
        Constant.init(this)
    }

    companion object {
        private lateinit var app: App
        val context get() = app
    }
}