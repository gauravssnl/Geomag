package com.sanmer.geomag.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun Logo(
    @DrawableRes iconRes: Int,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    containerColor: Color = MaterialTheme.colorScheme.primary
) = Box(
    modifier = modifier
        .clip(CircleShape)
        .shadow(elevation = 10.dp)
        .background(color = containerColor),
    contentAlignment = Alignment.Center
) {
    Icon(
        modifier = Modifier.fillMaxSize(0.6f),
        painter = painterResource(id = iconRes),
        contentDescription = null,
        tint = contentColor
    )
}

@Composable
fun Logo(
    text: String,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold
    )
) = Box(
    modifier = modifier
        .clip(CircleShape)
        .shadow(elevation = 10.dp)
        .background(color = containerColor),
    contentAlignment = Alignment.Center
) {
    Box(
        modifier = Modifier
            .fillMaxSize(0.6f)
            .background(
                color = containerColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        val label by remember(text) {
            derivedStateOf {
                if (text.isNotEmpty()) {
                    text.first().toString()
                } else {
                    "?"
                }
            }
        }

        Text(
            text = label,
            color = contentColor,
            style = textStyle
        )
    }
}