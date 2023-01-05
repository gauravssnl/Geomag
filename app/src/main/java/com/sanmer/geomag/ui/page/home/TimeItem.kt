package com.sanmer.geomag.ui.page.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sanmer.geomag.R
import com.sanmer.geomag.core.time.DateTime
import com.sanmer.geomag.core.time.TimerManager
import com.sanmer.geomag.ui.component.CardItem
import com.sanmer.geomag.utils.toIntOr
import com.sanmer.geomag.utils.toIntOrZero
import com.sanmer.geomag.viewmodel.HomeViewModel

@Composable
fun TimeItem(
    viewModel: HomeViewModel = viewModel()
) {
    var showEditor by remember { mutableStateOf(false) }

    CardItem(
        iconRes = R.drawable.timer_outline,
        label = stringResource(id = R.string.time_title),
        trailingIcon = {
            IconButton(
                onClick = {
                    viewModel.changeTimeServiceState()
                }
            ) {
                Icon(
                    painter = painterResource(id = if (viewModel.isTSRunning) {
                        R.drawable.timer_pause_outline
                    } else {
                        R.drawable.timer_start_outline
                    }),
                    contentDescription = null
                )
            }
        }
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            text = stringResource(id = R.string.time_desc, viewModel.model.label),
            style = MaterialTheme.typography.bodyMedium
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.outline
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(id = R.string.time_time, viewModel.dateTime),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(id = R.string.time_decimal, viewModel.decimalYears),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Button(
                onClick = { showEditor = true }
            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    painter = painterResource(id = R.drawable.edit_outline),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = stringResource(id = R.string.button_edit))
            }
        }

        if (showEditor) {
            EditTime(
                onClose = { showEditor = false },
                onConfirm = {
                    if (viewModel.isTSRunning) {
                        TimerManager.stop()
                    }
                    viewModel.editDateTime(it)
                }
            )
        }
    }
}

@Composable
private fun EditTime(
    onClose: () -> Unit,
    onConfirm: (DateTime) -> Unit,
    onDismiss: () -> Unit = {}
) {
    var year by rememberSaveable { mutableStateOf("") }
    var month by rememberSaveable { mutableStateOf("") }
    var day by rememberSaveable { mutableStateOf("") }
    var hour by rememberSaveable { mutableStateOf("") }
    var minute by rememberSaveable { mutableStateOf("") }
    var second by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onClose,
        title = { Text(text = stringResource(id = R.string.time_title)) },
        confirmButton = {
            TextButton(
                onClick = {
                    val dateTime = DateTime(
                        year = year.toIntOr(1900),
                        month = month.toIntOr(1),
                        day = day.toIntOr(1),
                        hour = hour.toIntOrZero(),
                        minute = minute.toIntOrZero(),
                        second = second.toIntOrZero()
                    )
                    onConfirm(dateTime)
                    onClose()
                }
            ) {
                Text(text = stringResource(id = R.string.dialog_ok))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                    onClose()
                }
            ) {
                Text(text = stringResource(id = R.string.dialog_cancel))
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(0.4f),
                        value = year,
                        onValueChange = {
                            year = it
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        shape = RoundedCornerShape(12.dp),
                        placeholder = { Text(text = "YYYY") },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyLarge
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(0.5f),
                        value = month,
                        onValueChange = {
                            month = it
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        shape = RoundedCornerShape(12.dp),
                        placeholder = { Text(text = "MM") },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyLarge
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = day,
                        onValueChange = {
                            day = it
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        shape = RoundedCornerShape(12.dp),
                        placeholder = { Text(text = "DD") },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyLarge
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(0.35f),
                        value = hour,
                        onValueChange = {
                            hour = it
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        shape = RoundedCornerShape(12.dp),
                        placeholder = { Text(text = "HH") },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyLarge
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(0.5f),
                        value = minute,
                        onValueChange = {
                            minute = it
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        shape = RoundedCornerShape(12.dp),
                        placeholder = { Text(text = "MM") },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyLarge
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = second,
                        onValueChange = {
                            second = it
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        shape = RoundedCornerShape(12.dp),
                        placeholder = { Text(text = "SS") },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyLarge
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(start = 12.dp),
                    text = stringResource(id = R.string.dialog_empty_zero)
                )
            }
        }
    )
}