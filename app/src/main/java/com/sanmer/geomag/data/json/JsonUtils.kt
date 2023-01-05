package com.sanmer.geomag.data.json

import android.content.Context
import com.sanmer.geomag.core.time.DateTime
import com.sanmer.geomag.data.record.Record
import com.sanmer.geomag.utils.MediaStoreUtils.share
import com.sanmer.geomag.utils.MediaStoreUtils.toCache
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter

object JsonUtils {
    private val moshi = Moshi.Builder().build()
    private val record = moshi.adapter<RecordJson>()
    private val records = moshi.adapter<List<RecordJson>>()

    private fun toJson(value: Record): String? {
        return record.indent("    ").toJson(value.toJson())
    }

    private fun toJson(values: List<Record>): String? {
        val list = values.map { it.toJson() }
        return records.indent("    ").toJson(list)
    }

    fun share(context: Context, value: Record) {
        val jsonString = toJson(value)
        val name = "${value.time}.json".replace(" ", "T")
        val file = context.toCache(jsonString, name)
        file.share(context, "text/json")
    }

    fun share(context: Context, values: List<Record>) {
        val jsonString = toJson(values)
        val name = "${DateTime.now()}.json".replace(" ", "T")
        val file = context.toCache(jsonString, name)
        file.share(context, "text/json")
    }

    fun deleteJson(context: Context) {
        context.cacheDir.list { _, name ->
            if (name.endsWith("json")) {
                context.cacheDir.resolve(name).delete()
            }
            false
        }
    }
}