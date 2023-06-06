package com.sanmer.geomag.utils.expansion

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import com.sanmer.geomag.BuildConfig
import java.io.File

val Context.jsonDir get() = cacheDir.resolve("json")

val Context.logDir get() = cacheDir.resolve("log")

fun Context.deleteLog(name: String) {
    logDir.listFiles().orEmpty()
        .forEach {
            if (it.name.startsWith(name) && it.isFile) {
                it.delete()
            }
        }
}

fun Context.openUrl(url: String) {
    Intent.parseUri(url, Intent.URI_INTENT_SCHEME).apply {
        startActivity(this)
    }
}

fun Context.getUriForFile(file: File): Uri {
    return FileProvider.getUriForFile(this,
        "${BuildConfig.APPLICATION_ID}.provider", file
    )
}

fun Context.shareFile(file: File, mimeType: String) {
    ShareCompat.IntentBuilder(this)
        .setType(mimeType)
        .addStream(getUriForFile(file))
        .startChooser()
}

fun Context.navigateToLauncher() {
    val home = Intent(Intent.ACTION_MAIN).apply {
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        addCategory(Intent.CATEGORY_HOME)
    }
    startActivity(home)
}