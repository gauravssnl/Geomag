package com.sanmer.geomag.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanmer.geomag.data.Constant
import com.sanmer.geomag.data.record.Record
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val index: Int? = savedStateHandle["index"]
    var record: Record? = null
        private set

    init {
        Timber.d("DetailViewModel init: $index")
        getRecord()
    }

    private fun getRecord() = viewModelScope.launch(Dispatchers.IO) {
        if (index != null) {
            runCatching {
                Constant.getAll()[index]
            }.onSuccess {
                record = it
            }.onFailure {
                Timber.d("getRecord: ${it.message}")
            }
        }
    }

    fun delete() = viewModelScope.launch {
        Constant.delete(record!!)
    }
}