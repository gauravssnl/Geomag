package com.sanmer.geomag.ui.screens.regular

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sanmer.geomag.R
import com.sanmer.geomag.app.Config
import com.sanmer.geomag.core.models.Models
import com.sanmer.geomag.core.models.getModel
import com.sanmer.geomag.core.models.models
import com.sanmer.geomag.ui.component.CardItem
import com.sanmer.geomag.ui.component.DropdownMenu
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
                selected = Config.MODEL
            ) {
                Config.MODEL = it.id
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
fun ModelSelect(
    selected: Int,
    onClick: (Models) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val animateZ by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            )
    )

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        shape = RoundedCornerShape(15.dp),
        contentAlignment = Alignment.BottomEnd,
        surface = {
            FilterChip(
                selected = true,
                onClick = { expanded = true },
                label = { Text(text = getModel(selected).label) },
                trailingIcon = {
                    Icon(
                        modifier = Modifier
                            .size(16.dp)
                            .graphicsLayer {
                                rotationZ = animateZ
                            },
                        painter = painterResource(id = R.drawable.arrow_down_bold),
                        contentDescription = null
                    )
                }
            )
        }
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

@Composable
private fun MenuItem(
    value: Models,
    selected: Int,
    onClick: () -> Unit
) = DropdownMenuItem(
    modifier = Modifier
        .background(
            if (value.id == selected) {
                MaterialTheme.colorScheme.secondaryContainer
            } else {
                Color.Unspecified
            }
        ),
    enabled = value.enable,
    text = { Text(text = value.label) },
    onClick = onClick
)
