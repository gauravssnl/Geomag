package com.sanmer.geomag.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.sanmer.geomag.model.Record
import timber.log.Timber

class DetailViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val index: Int? = savedStateHandle["index"]
    var record: Record = Record.empty()
        private set

    init {
        Timber.d("DetailViewModel init: $index")
    }
}