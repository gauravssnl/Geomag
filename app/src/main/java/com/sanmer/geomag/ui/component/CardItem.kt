package com.sanmer.geomag.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.sanmer.geomag.ui.utils.Logo

@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int? = null,
    onClick: (() -> Unit)? = null,
    clickable: Boolean = false,
    label: String,
    trailingIcon: @Composable (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit = {}
) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp)
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth(fraction = if (trailingIcon == null) {
                            1f
                        } else{
                            0.6f
                        }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (iconRes != null) {
                        Logo(
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(40.dp),
                            iconRes = iconRes,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                            clickable = clickable,
                            onClick = onClick ?: {}
                        )
                    }

                    Text(
                        text = label,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    trailingIcon?.let { it() }
                }
            }
            content()
        }
    }
}