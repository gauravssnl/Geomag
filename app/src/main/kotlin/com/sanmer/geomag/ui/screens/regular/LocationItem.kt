package com.sanmer.geomag.ui.screens.regular

import android.content.Context
import android.content.Intent
import android.location.Location
import android.provider.Settings
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sanmer.geomag.R
import com.sanmer.geomag.core.localtion.AppLocationManager
import com.sanmer.geomag.data.record.toPosition
import com.sanmer.geomag.service.LocationService
import com.sanmer.geomag.ui.component.CardItem
import com.sanmer.geomag.utils.expansion.toDoubleOrZero
import com.sanmer.geomag.viewmodel.HomeViewModel
import timber.log.Timber

@Composable
fun LocationItem(
    viewModel: HomeViewModel = viewModel(),
) {
    val context = LocalContext.current

    var location by remember { mutableStateOf(viewModel.locationOrZero) }
    var edit by remember { mutableStateOf(false) }

    val isEnable = AppLocationManager.isEnable
    val isReady = AppLocationManager.isReady
    var iconRes by remember { mutableStateOf(R.drawable.location_slash_outline) }
    var onClick = { AppLocationManager.launchPermissionRequest() }

    AppLocationManager.PermissionsState(
        onGranted = {
            iconRes = R.drawable.location_outline
            onClick = { viewModel.getLastKnownLocation() }
        },
        onDenied = {
            iconRes = R.drawable.location_cross_outline
        }
    )

    var isNeeded by remember { mutableStateOf(false) }
    if (isNeeded) LocationServiceDialog { isNeeded = false }

    CardItem(
        iconRes = if (isEnable) iconRes else R.drawable.location_slash_outline,
        onClick = if (isEnable) { onClick } else { { isNeeded = true } },
        clickable = true,
        label = stringResource(id = R.string.location_title),
        trailingIcon = {
            IconButton(
                onClick = {
                    if (isEnable) {
                        if (!isReady) {
                            AppLocationManager.launchPermissionRequest()
                        } else {
                            if (!edit) viewModel.toggleLocation(context)
                        }
                    } else {
                        isNeeded = true
                    }
                }
            ) {
                Icon(
                    painter = painterResource(id = if (viewModel.isLSRunning) {
                        R.drawable.gps_outline
                    } else {
                        R.drawable.gps_slash_outline
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
            text = stringResource(id = R.string.location_desc),
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
                    Modifier
                        .clickable {
                            location = viewModel.locationOrZero
                            edit = true
                        }
                        .padding(16.dp)
                } else {
                    Modifier.padding(16.dp)
                }),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            if (edit) {
                EditLocation(
                    location = location,
                    onValueChange = { location = it }
                ) {
                    viewModel.editLocation(location)
                    edit = false

                    defaultKeyboardAction(ImeAction.Done)
                    it.clearFocus()
                }
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
            }
        }
    }
}

@Composable
fun EditLocation(
    viewModel: HomeViewModel = viewModel(),
    location: Location,
    onValueChange: (Location) -> Unit,
    onDone: KeyboardActionScope.(FocusManager) -> Unit
) {
    val context = LocalContext.current

    var altitude by remember {
        mutableStateOf(TextFieldValue(
            text = location.altitude.toString(),
            selection = TextRange("${location.altitude}".length)
        ))
    }
    var latitude by remember {
        mutableStateOf(TextFieldValue(
            text = location.latitude.toString(),
            selection = TextRange("${location.latitude}".length)
        ))
    }
    var longitude by remember {
        mutableStateOf(TextFieldValue(
            text = location.longitude.toString(),
            selection = TextRange("${location.longitude}".length)
        ))
    }
    val getLocation = location.apply {
        location.altitude = altitude.text.toDoubleOrZero() * 1000
        location.latitude = latitude.text.toDoubleOrZero()
        location.longitude = longitude.text.toDoubleOrZero()
    }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
        keyboardController?.show()

        if (viewModel.isLSRunning) {
            LocationService.stop(context)
        }
    }

    EditItem(
        focusRequester = focusRequester,
        value = altitude,
        onValueChange = { altitude = it },
        prefix = stringResource(id = R.string.location_altitude, ""),
        suffix = " km",
        onDone = {
            onValueChange(getLocation)
            onDone(this, focusManager)
        }
    )

    EditItem(
        focusRequester = focusRequester,
        value = latitude,
        onValueChange = { latitude = it },
        prefix = stringResource(id = R.string.location_latitude, ""),
        suffix = "º N",
        onDone = {
            onValueChange(getLocation)
            onDone(this, focusManager)
        }
    )

    EditItem(
        focusRequester = focusRequester,
        value = longitude,
        onValueChange = { longitude = it },
        prefix = stringResource(id = R.string.location_longitude, ""),
        suffix = "º W",
        onDone = {
            onValueChange(getLocation)
            onDone(this, focusManager)
        }
    )
}

@Composable
fun EditItem(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    focusRequester: FocusRequester,
    prefix: String,
    suffix: String? = null,
    textColor: Color = LocalContentColor.current,
    onDone: KeyboardActionScope.() -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = prefix,
            style = MaterialTheme.typography.bodyLarge
        )

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .defaultMinSize(
                    minWidth = 2.dp
                )
                .width(IntrinsicSize.Min)
                .focusRequester(focusRequester),
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                textDecoration = TextDecoration.Underline,
                color = textColor
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = onDone),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
        )

        suffix?.let {
            Text(
                text = suffix,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun LocationServiceDialog(
    context: Context = LocalContext.current,
    onClose: () -> Unit
) = AlertDialog(
    shape = RoundedCornerShape(20.dp),
    onDismissRequest = onClose,
    title = {
        Text(text = stringResource(id = R.string.dialog_title_attention))
    },
    text = {
        Text(text = stringResource(id = R.string.location_dialog_desc))
    },
    confirmButton = {
        TextButton(
            onClick = {
                context.startActivity(Intent().apply {
                    action = Settings.ACTION_LOCATION_SOURCE_SETTINGS
                })
                onClose()
            }
        ) {
            Text(
                text = stringResource(id = R.string.dialog_ok)
            )
        }
    },
    dismissButton = {
        TextButton(
            onClick = onClose
        ) {
            Text(
                text = stringResource(id = R.string.dialog_cancel)
            )
        }
    }
)