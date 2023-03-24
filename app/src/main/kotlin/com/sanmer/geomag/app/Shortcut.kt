package com.sanmer.geomag.app

import android.content.Intent
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.sanmer.geomag.App
import com.sanmer.geomag.BuildConfig
import com.sanmer.geomag.R
import com.sanmer.geomag.ui.activity.log.LogActivity

object Shortcut {
    private val context by lazy { App.context }
    private const val ID_LOGS = "logs"
    private const val ID_SETTINGS = "settings"
    private const val ID_RECORDS = "records"
    const val ACTION_RECORDS = "${BuildConfig.APPLICATION_ID}.shortcut.RECORDS"
    const val ACTION_SETTINGS = "${BuildConfig.APPLICATION_ID}.shortcut.SETTINGS"

    val logs get() = run {
        val activity = Intent(Intent.ACTION_MAIN, null, context, LogActivity::class.java)

        ShortcutInfoCompat.Builder(context, ID_LOGS)
            .setShortLabel(context.getString(R.string.shortcut_log_label))
            .setLongLabel(context.getString(R.string.shortcut_log_label))
            .setIcon(IconCompat.createWithResource(context, R.drawable.shortcut_log))
            .setIntent(activity)
            .build()
    }

    val settings get() = run {
        val page = Intent(ACTION_SETTINGS)

        ShortcutInfoCompat.Builder(context, ID_SETTINGS)
            .setShortLabel(context.getString(R.string.shortcut_settings_label))
            .setLongLabel(context.getString(R.string.shortcut_settings_label))
            .setIcon(IconCompat.createWithResource(context, R.drawable.shortcut_settings))
            .setIntent(page)
            .build()
    }

    val records get() = run {
        val page = Intent(ACTION_RECORDS)

        ShortcutInfoCompat.Builder(context, ID_RECORDS)
            .setShortLabel(context.getString(R.string.shortcut_records_label))
            .setLongLabel(context.getString(R.string.shortcut_records_label))
            .setIcon(IconCompat.createWithResource(context, R.drawable.shortcut_records))
            .setIntent(page)
            .build()
    }

    fun push() {
        ShortcutManagerCompat.pushDynamicShortcut(context, logs)
        ShortcutManagerCompat.pushDynamicShortcut(context, settings)
        ShortcutManagerCompat.pushDynamicShortcut(context, records)
    }
}