package com.sanmer.geomag.utils

import com.sanmer.geomag.App
import com.sanmer.geomag.model.Record
import com.sanmer.geomag.model.json.RecordJson
import com.sanmer.geomag.model.json.toJson
import com.sanmer.geomag.utils.expansion.jsonDir
import com.sanmer.geomag.utils.expansion.now
import com.sanmer.geomag.utils.expansion.shareFile
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import kotlinx.datetime.LocalDateTime

object JsonUtils {
    val context by lazy { App.context }
    private val moshi = Moshi.Builder().build()
    private val record = moshi.adapter<RecordJson>()
    private val records = moshi.adapter<List<RecordJson>>()

    init {
        context.jsonDir.deleteRecursively()
    }

    private fun Record.toJsonText(): String? {
        return record.indent("    ").toJson(toJson())
    }

    private fun List<Record>.toJsonText(): String? {
        val list = map { it.toJson() }
        return records.indent("    ").toJson(list)
    }

    fun shareJsonFile(value: Record) {
        val file = context.jsonDir
            .resolve("${value.time}.json")
            .apply {
                writeText(value.toJsonText()!!)
            }

        context.shareFile(file, "text/json")
    }

    fun shareJsonFile(values: List<Record>) {
        val file = context.jsonDir
            .resolve("${LocalDateTime.now()}.json")
            .apply {
                writeText(values.toJsonText()!!)
            }

        context.shareFile(file, "text/json")
    }
}