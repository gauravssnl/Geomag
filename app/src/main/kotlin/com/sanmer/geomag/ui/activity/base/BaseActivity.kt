package com.sanmer.geomag.ui.activity.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import com.sanmer.geomag.ui.theme.AppTheme
import com.sanmer.geomag.ui.theme.Colors

abstract class BaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    fun setActivityContent(
        content: @Composable () -> Unit
    ) = setContent {
        AppTheme(
            darkMode = isSystemInDarkTheme(),
            themeColor = Colors.Dynamic.id
        ) {
            content()
        }
    }
}