package com.sanmer.geomag.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanmer.geomag.data.Constant
import com.sanmer.geomag.data.json.JsonUtils
import com.sanmer.geomag.data.record.Record
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class RecordsViewModel : ViewModel() {
    val records = mutableStateListOf<Record>()

    private val out = mutableStateListOf<Record>()
    var chooser by mutableStateOf(false)
    val size: Int get() = out.size

    init {
        Timber.d("RecordViewModel init")
    }

    fun getAll() = viewModelScope.launch {
        if (records.isNotEmpty()) {
            records.clear()
        }

        val list = Constant.getAll()
        records.addAll(list)
    }

    fun toggle(value: Record) {
        if (isSelected(value)) {
            out.remove(value)
        } else {
            out.add(value)
        }
    }

    fun close() {
        chooser = false
        out.clear()
    }

    fun isSelected(value: Record) = value in out
    fun isEmpty() = out.isEmpty()

    fun share(context: Context) {
        JsonUtils.share(context, out)
        out.clear()
    }

    fun delete() = viewModelScope.launch(Dispatchers.IO) {
        Constant.delete(out)
        records.removeAll(out)
        out.clear()
    }
}