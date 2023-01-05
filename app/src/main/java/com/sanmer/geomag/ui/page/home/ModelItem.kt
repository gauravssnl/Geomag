package com.sanmer.geomag.ui.page.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sanmer.geomag.R
import com.sanmer.geomag.core.models.Models
import com.sanmer.geomag.core.models.getModels
import com.sanmer.geomag.core.models.models
import com.sanmer.geomag.ui.component.CardItem
import com.sanmer.geomag.ui.utils.HtmlText
import com.sanmer.geomag.viewmodel.HomeViewModel

@Composable
fun ModelItem(
    viewModel: HomeViewModel = viewModel(),
) {

    CardItem(
        iconRes = R.drawable.chart_outline,
        label = stringResource(id = R.string.model_title),
        trailingIcon = {
            ModelSelect(
                selected = viewModel.model.key
            ) {
                viewModel.updateModel(it)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.model_model,
                    stringResource(id = viewModel.model.name)),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(id = R.string.model_validity_period,
                    viewModel.model.start, viewModel.model.end),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(10.dp))
            HtmlText(
                text = stringResource(
                    id = R.string.model_web,
                    "<b><a href=\"${viewModel.model.web}\">${viewModel.model.label}</a></b>"
                ),
                style = MaterialTheme.typography.bodyMedium
            )

        }
    }
}

@Composable
private fun ModelSelect(
    selected: String,
    onClick: (Models) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        FilterChip(
            selected = true,
            onClick = {
                expanded = true
            },
            label = {
                Text(text = getModels(selected).label)
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .size(16.dp),
                    painter = painterResource(
                        id = if (expanded) {
                            R.drawable.arrow_up_bold
                        } else {
                            R.drawable.arrow_down_bold
                        }
                    ),
                    contentDescription = null
                )
            }
        )

        CustomShape {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.BottomEnd)
            ) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    models.forEach {
                        MenuItem(
                            value = it,
                            selected = selected
                        ) {
                            if (it.enable) {
                                onClick(it)
                                expanded = false
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MenuItem(
    value: Models,
    selected: String,
    onClick: () -> Unit
) = DropdownMenuItem(
    modifier = Modifier
        .background(
            if (value.key == selected) {
                MaterialTheme.colorScheme.secondaryContainer
            } else {
                Color.Unspecified
            }
        ),
    enabled = value.enable,
    text = { Text(text = value.label) },
    onClick = onClick
)

@Composable
private fun CustomShape(
    content: @Composable () -> Unit
) = MaterialTheme(
    shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(12.dp)),
    content = content
)
