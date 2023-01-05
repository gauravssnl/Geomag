package com.sanmer.geomag.utils

import android.content.Context
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import java.io.File

object MediaStoreUtils {
    fun Context.toCache(input: String?, name: String): File {
        val out = cacheDir.resolve(name)
        out.deleteOnExit()

        val output = out.outputStream()
        output.write(input?.toByteArray())
        output.close()

        return out
    }

    fun File.share(context: Context, mimeType: String) {
        val uri = FileProvider.getUriForFile(context,
                "${context.packageName}.provider", this)

        ShareCompat.IntentBuilder(context)
            .setType(mimeType)
            .addStream(uri)
            .startChooser()
    }
}
