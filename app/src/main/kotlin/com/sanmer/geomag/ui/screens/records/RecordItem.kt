package com.sanmer.geomag.ui.screens.records

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sanmer.geomag.R
import com.sanmer.geomag.data.record.Record
import com.sanmer.geomag.viewmodel.RecordsViewModel

@Composable
fun RecordItem(
    viewModel: RecordsViewModel = viewModel(),
    record: Record,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .combinedClickable(
                enabled = true,
                onClick = {
                    if (viewModel.chooser) {
                        viewModel.toggle(record)
                    } else {
                        onClick()
                    }
                },
                onLongClick = {
                    if (!viewModel.chooser) {
                        viewModel.chooser = true
                        viewModel.toggle(record)
                    }
                },
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = true,
                    color = MaterialTheme.colorScheme.primary
                )
            )
            .padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(
                    color = if (viewModel.isSelected(record)) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            if (viewModel.isSelected(record)) {
                Icon(
                    modifier = Modifier
                        .size(16.dp),
                    painter = painterResource(id = R.drawable.tick_outline),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = null
                )
            } else {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = record.model.first().toString(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = record.location.toString(),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = record.time.toString(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}