package com.sanmer.geomag.utils

import android.content.Context
import com.sanmer.geomag.App
import com.sanmer.geomag.data.json.RecordJson
import com.sanmer.geomag.data.json.toJson
import com.sanmer.geomag.data.record.Record
import com.sanmer.geomag.utils.expansion.now
import com.sanmer.geomag.utils.expansion.shareFile
import com.sanmer.geomag.utils.expansion.toCacheDir
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import kotlinx.datetime.LocalDateTime

object JsonUtils {
    val context by lazy { App.context }
    private val moshi = Moshi.Builder().build()
    private val record = moshi.adapter<RecordJson>()
    private val records = moshi.adapter<List<RecordJson>>()

    init {
        deleteJson(context)
    }

    private fun toJson(value: Record): String? {
        return record.indent("    ").toJson(value.toJson())
    }

    private fun toJson(values: List<Record>): String? {
        val list = values.map { it.toJson() }
        return records.indent("    ").toJson(list)
    }

    fun share(context: Context, value: Record) {
        val jsonString = toJson(value)
        val name = "json/${value.time}.json"
        val file = context.toCacheDir(jsonString, name)
        context.shareFile(file, "text/json")
    }

    fun share(context: Context, values: List<Record>) {
        val jsonString = toJson(values)
        val name = "json/${LocalDateTime.now()}.json"
        val file = context.toCacheDir(jsonString, name)
        context.shareFile(file, "text/json")
    }

    private fun deleteJson(context: Context) {
        context.cacheDir.resolve("json").deleteRecursively()
    }
}