package com.sanmer.geomag.ui.screens.simple

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sanmer.geomag.R
import com.sanmer.geomag.core.time.TimerManager
import com.sanmer.geomag.core.time.toDateTime
import com.sanmer.geomag.ui.component.CardItem
import com.sanmer.geomag.ui.screens.regular.EditLocation
import com.sanmer.geomag.ui.screens.regular.EditTime
import com.sanmer.geomag.ui.screens.regular.ModelSelect
import com.sanmer.geomag.viewmodel.HomeViewModel

@Composable
fun InputItem(
    viewModel: HomeViewModel = viewModel(),
) {
    SideEffect {
        if (viewModel.isTSRunning) {
            TimerManager.stop()
        }
    }

    var location by remember { mutableStateOf(viewModel.locationOrZero) }
    var dateTime by remember { mutableStateOf(TextFieldValue("", TextRange(0))) }
    var fail by remember { mutableStateOf(false) }
    var edit by remember { mutableStateOf(false) }

    val onDone: KeyboardActionScope.(FocusManager) -> Unit = {
        try {
            viewModel.editLocation(location)
            viewModel.editDateTime(dateTime.text.toDateTime())
            edit = false
            fail = false

            defaultKeyboardAction(ImeAction.Done)
            it.clearFocus()
        } catch (e: Exception) {
            fail = true
        }
    }

    CardItem(
        iconRes = R.drawable.edit_outline,
        label = stringResource(id = R.string.simple_input),
        trailingIcon = {
            ModelSelect(
                selected = viewModel.model.id
            ) {
                viewModel.updateModel(it)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp))
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(15.dp),
                    color = MaterialTheme.colorScheme.outline
                )
                .then(if (!edit) {
                    Modifier.clickable {
                        location = viewModel.locationOrZero
                        dateTime = TextFieldValue(viewModel.dateTime.toString(), TextRange(19))
                        edit = true
                    }.padding(16.dp)
                } else {
                    Modifier.padding(16.dp)
                }),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            if (edit) {
                EditLocation(
                    location = location,
                    onValueChange = { location = it },
                    onDone = onDone
                )
                EditTime(
                    dateTime = dateTime,
                    onValueChange = { dateTime = it },
                    textColor = if (fail) {
                        MaterialTheme.colorScheme.error
                    } else {
                        LocalContentColor.current
                    },
                    onDone = onDone
                )
            } else {
                Text(
                    text = stringResource(
                        id = R.string.location_altitude,
                        "${viewModel.locationOrZero.altitude} km"
                    ),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(
                        id = R.string.location_latitude,
                        "${viewModel.locationOrZero.latitude}º N"
                    ),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(
                        id = R.string.location_longitude,
                        "${viewModel.locationOrZero.longitude}º W"
                    ),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(id = R.string.time_time, viewModel.dateTime),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Text(
                text = stringResource(id = R.string.time_decimal, viewModel.decimalYears),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}