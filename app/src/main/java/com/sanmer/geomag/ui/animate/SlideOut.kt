package com.sanmer.geomag.ui.animate

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOut
import androidx.compose.ui.unit.IntOffset

object SlideOut {
    val topToBottom = slideOut(
        targetOffset = { IntOffset(0,  it.height) },
        animationSpec = tween(300)
    )

    val bottomToTop = slideOut(
        targetOffset = { IntOffset(0,  - it.height) },
        animationSpec = tween(300)
    )

    val rightToLeft = slideOut(
        targetOffset = { IntOffset(- it.width,  0) },
        animationSpec = tween(300)
    )

    val leftToRight = slideOut(
        targetOffset = { IntOffset(it.width,  0) },
        animationSpec = tween(300)
    )
}