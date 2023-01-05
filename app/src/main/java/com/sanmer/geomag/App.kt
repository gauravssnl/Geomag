package com.sanmer.geomag

import android.app.Application
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.sanmer.geomag.app.Config
import com.sanmer.geomag.core.localtion.AppLocationManager
import com.sanmer.geomag.core.models.Geomag
import com.sanmer.geomag.core.time.TimerManager
import com.sanmer.geomag.data.Constant
import com.sanmer.geomag.utils.NotificationUtils
import com.sanmer.geomag.utils.timber.DebugTree
import com.sanmer.geomag.utils.timber.ReleaseTree
import com.tencent.mmkv.MMKV
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }

        MMKV.initialize(this)
        AppLocationManager.init(this)
        TimerManager.init()
        Constant.init(this)
        Geomag.init()
        NotificationUtils.init(this)

        Firebase.crashlytics.setCrashlyticsCollectionEnabled(Config.ANALYTICS_COLLECTION)
        Firebase.analytics.setAnalyticsCollectionEnabled(Config.ANALYTICS_COLLECTION)
        Timber.i("Firebase data collection: ${Config.ANALYTICS_COLLECTION}")
    }
}