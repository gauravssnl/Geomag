package com.sanmer.geomag

import android.app.Application
import com.sanmer.geomag.core.localtion.AppLocationManager
import com.sanmer.geomag.core.models.Geomag
import com.sanmer.geomag.core.time.TimerManager
import com.sanmer.geomag.data.Constant
import com.sanmer.geomag.utils.NotificationUtils
import com.sanmer.geomag.utils.SPUtils
import com.sanmer.geomag.utils.timber.DebugTree
import com.sanmer.geomag.utils.timber.ReleaseTree
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }

        AppLocationManager.init(this)
        TimerManager.init()
        Constant.init(this)
        Geomag.init()
        SPUtils.init(this)
        NotificationUtils.init(this)

    }
}