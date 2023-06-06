package com.sanmer.geomag.ui.activity.main

import android.os.Build
import android.os.Bundle
import com.sanmer.geomag.app.utils.LocationManagerUtils
import com.sanmer.geomag.app.utils.NotificationUtils
import com.sanmer.geomag.ui.activity.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityContent {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                NotificationUtils.PermissionState()
            }

            LocationManagerUtils.PermissionsState()

            if (it.enableNavigationAnimation) {
                AnimatedMainScreen()
            } else {
                NormalMainScreen()
            }
        }
    }
}