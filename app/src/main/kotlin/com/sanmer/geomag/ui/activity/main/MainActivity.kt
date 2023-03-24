package com.sanmer.geomag.ui.activity.main

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.sanmer.geomag.app.Shortcut
import com.sanmer.geomag.ui.theme.AppTheme
import com.sanmer.geomag.utils.NotificationUtils

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        Shortcut.push()

        setContent {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                NotificationUtils.PermissionState()
            }

            AppTheme {
                MainScreen()
            }
        }
    }
}