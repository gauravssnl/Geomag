package com.sanmer.geomag.ui.screens.regular

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
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
import com.sanmer.geomag.viewmodel.HomeViewModel

@Composable
fun TimeItem(
    viewModel: HomeViewModel = viewModel()
) {
    var dateTime by remember { mutableStateOf(TextFieldValue("", TextRange(0))) }
    var fail by remember { mutableStateOf(false) }
    var edit by remember { mutableStateOf(false) }

    val onDone: KeyboardActionScope.(FocusManager) -> Unit = {
        try {
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
        iconRes = R.drawable.timer_outline,
        label = stringResource(id = R.string.time_title),
        trailingIcon = {
            IconButton(
                onClick = {
                    if (!edit) viewModel.toggleTime()
                }
            ) {
                Icon(
                    painter = painterResource(
                        id = if (viewModel.isTSRunning) {
                            R.drawable.timer_pause_outline
                        } else {
                            R.drawable.timer_start_outline
                        }
                    ),
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
                .clip(RoundedCornerShape(15.dp))
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(15.dp),
                    color = MaterialTheme.colorScheme.outline
                )
                .then(if (!edit) {
                    Modifier.clickable {
                        dateTime = TextFieldValue(viewModel.dateTime.toString(), TextRange(19))
                        edit = true
                    }.padding(16.dp)
                } else {
                    Modifier.padding(16.dp)
                }),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (edit) {
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

@Composable
fun EditTime(
    viewModel: HomeViewModel = viewModel(),
    dateTime: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    textColor: Color = LocalContentColor.current,
    onDone: KeyboardActionScope.(FocusManager) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
        keyboardController?.show()

        if (viewModel.isTSRunning) {
            TimerManager.stop()
        }
    }

    EditItem(
        focusRequester = focusRequester,
        value = dateTime,
        onValueChange = onValueChange,
        prefix = stringResource(id = R.string.time_time, ""),
        textColor = textColor,
        onDone = {
            onDone(this, focusManager)
        }
    )
}