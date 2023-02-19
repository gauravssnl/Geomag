package com.sanmer.geomag.ui.utils

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun Logo(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    clickable: Boolean = false,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(color = backgroundColor)
            .clickable(enabled = clickable, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .fillMaxSize(0.6f),
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = contentColor
        )
    }
}