package com.sanmer.geomag.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sanmer.geomag.ui.utils.Logo

@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    label: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit = {}
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                leadingIcon?.let {
                    leadingIcon()
                    Spacer(modifier = Modifier.width(16.dp))
                }
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = label,
                    style = MaterialTheme.typography.titleMedium
                )

                trailingIcon?.let {
                    Spacer(modifier = Modifier.width(16.dp))
                    trailingIcon()
                }
            }
            content()
        }
    }
}

@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    onClick: (() -> Unit)? = null,
    clickable: Boolean = false,
    label: String,
    trailingIcon: @Composable (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit = {}
) = CardItem(
    modifier = modifier,
    label = label,
    leadingIcon = {
        Logo(
            modifier = Modifier
                .size(40.dp),
            iconRes = iconRes,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            clickable = clickable,
            onClick = onClick ?: {}
        )
    },
    trailingIcon = trailingIcon,
    content = content
)