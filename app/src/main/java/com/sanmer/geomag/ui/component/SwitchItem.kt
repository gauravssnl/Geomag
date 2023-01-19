package com.sanmer.geomag.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun SwitchItem(
    modifier: Modifier = Modifier,
    text: String,
    subText: String? = null,
    @DrawableRes iconRes: Int? = null,
    colorful: Boolean = false,
    enabled: Boolean = true,
    checked: Boolean,
    onChange: (Boolean) -> Unit,
) {
    Row(
        modifier = modifier
            .alpha(alpha = if (enabled) 1f else 0.5f )
            .selectable(
                enabled = enabled,
                selected = checked,
                onClick = {
                    onChange(!checked)
                },
                role = Role.Switch
            )
            .padding(all = 18.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        iconRes?.let {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = if (colorful) Color.Unspecified else LocalContentColor.current
            )

            Spacer(modifier = Modifier.width(18.dp))
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 18.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
            )
            subText?.let {
                Text(
                    text = subText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }

        Switch(
            checked = checked,
            onCheckedChange = null
        )

    }
}
