package com.sanmer.geomag.ui.activity.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sanmer.geomag.R
import com.sanmer.geomag.ui.component.PageIndicator

@Composable
fun MainScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        PageIndicator(
            icon = R.drawable.ic_logo,
            text = "Empty list"
        )
    }
}