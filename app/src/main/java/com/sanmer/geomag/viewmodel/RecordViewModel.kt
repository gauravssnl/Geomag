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

class RecordViewModel : ViewModel() {
    var chooser by mutableStateOf(false)
    val size: Int get() = out.size
    private val out = mutableStateListOf<Record>()

    fun change(value: Record) {
        if (isOwned(value)) {
            out.remove(value)
        } else {
            out.add(value)
        }
    }

    fun close() {
        chooser = false
        out.clear()
    }

    fun isOwned(value: Record) = value in out
    fun isEmpty() = out.isEmpty()

    fun share(context: Context) {
        JsonUtils.share(context, out)
        out.clear()
    }

    fun delete() {
        viewModelScope.launch(Dispatchers.IO) {
            Constant.delete(out)
            out.clear()
        }
    }

    init {
        Timber.d("RecordViewModel init")
    }
}