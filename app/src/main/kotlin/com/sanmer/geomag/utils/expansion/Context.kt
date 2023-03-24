package com.sanmer.geomag.utils.expansion

import android.content.Context
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import com.sanmer.geomag.BuildConfig
import java.io.File

fun Context.shareFile(file: File, mimeType: String) {
    val uri = FileProvider.getUriForFile(this,
        "${BuildConfig.APPLICATION_ID}.provider", file)

    ShareCompat.IntentBuilder(this)
        .setType(mimeType)
        .addStream(uri)
        .startChooser()
}

fun Context.toCacheDir(input: String?, name: String): File {
    val out = cacheDir.resolve(name)
    out.parentFile?.let {
        if (!it.exists()) {
            it.mkdirs()
        }
    }

    val output = out.outputStream()
    output.write(input?.toByteArray())
    output.close()

    return out
}