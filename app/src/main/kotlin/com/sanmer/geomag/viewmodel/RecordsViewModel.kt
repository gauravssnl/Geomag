package com.sanmer.geomag.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanmer.geomag.data.Constant
import com.sanmer.geomag.data.record.Record
import com.sanmer.geomag.utils.JsonUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

class RecordsViewModel : ViewModel() {
    val records = mutableStateListOf<Record>()

    private val out = mutableStateListOf<Record>()
    var chooser by mutableStateOf(false)
    val size get() = out.size

    init {
        Timber.d("RecordViewModel init")

        Constant.getAllAsFlow().onEach { list ->
            if (list.isEmpty()) return@onEach

            if (records.isNotEmpty()) records.clear()
            records.addAll(list)
            Timber.d("RecordsViewModel: update")

        }.launchIn(viewModelScope)
    }

    fun getAll() = viewModelScope.launch {
        val list = Constant.getAll()

        if (records.isNotEmpty()) records.clear()
        records.addAll(list)
        Timber.d("RecordsViewModel: update")
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