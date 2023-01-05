package com.sanmer.geomag.ui.page.home

import android.location.Location
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sanmer.geomag.R
import com.sanmer.geomag.core.localtion.AppLocationManager
import com.sanmer.geomag.service.LocationService
import com.sanmer.geomag.ui.component.CardItem
import com.sanmer.geomag.utils.toDoubleOrZero
import com.sanmer.geomag.viewmodel.HomeViewModel

@Composable
fun LocationItem(
    viewModel: HomeViewModel = viewModel(),
) {
    val owner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val location = viewModel.locationOrZero
    viewModel.observeLocation(owner)

    var isReady by remember { mutableStateOf(false) }
    var iconRes by remember { mutableStateOf(R.drawable.location_slash_outline) }
    var showEditor by remember { mutableStateOf(false) }

    AppLocationManager.PermissionsState(
        onGranted = {
            iconRes = R.drawable.location_outline
            isReady = true
        },
        onDenied = {
            iconRes = R.drawable.location_cross_outline
        }
    )

    LaunchedEffect(isReady) {
        if (isReady) {
            viewModel.requestSingleUpdate()
        }
    }

    CardItem(
        iconRes = iconRes,
        onClick = {
            if (!isReady) {
                AppLocationManager.launchPermissionRequest()
            }
        },
        clickable = !isReady,
        label = stringResource(id = R.string.location_title),
        trailingIcon = {
            IconButton(
                onClick = {
                    if (!isReady) {
                        AppLocationManager.launchPermissionRequest()
                    }
                    viewModel.changeLocationServiceState(context)
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
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.outline
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = stringResource(id = R.string.location_altitude, location.altitude),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(id = R.string.location_latitude, location.latitude),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(id = R.string.location_longitude, location.longitude),
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
            EditLocation(
                onClose = { showEditor = false },
                onConfirm = {
                    if (viewModel.isLSRunning) {
                        LocationService.stop(context)
                    }
                    viewModel.editLocation(it)
                }
            )
        }
    }
}

@Composable
private fun EditLocation(
    onClose: () -> Unit,
    onConfirm: (Location) -> Unit,
    onDismiss: () -> Unit = {}
) {
    var altitude by rememberSaveable { mutableStateOf("") }
    var latitude by rememberSaveable { mutableStateOf("") }
    var longitude by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onClose,
        title = { Text(text = stringResource(id = R.string.location_title)) },
        confirmButton = {
            TextButton(
                onClick = {
                    val location = Location(null)
                    location.altitude = altitude.toDoubleOrZero() * 1000
                    location.latitude = latitude.toDoubleOrZero()
                    location.longitude = longitude.toDoubleOrZero()

                    onConfirm(location)
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
                OutlinedTextField(
                    textStyle = MaterialTheme.typography.bodyLarge,
                    value = altitude,
                    onValueChange = { altitude = it },
                    label = { Text(text = stringResource(id = R.string.location_dialog_altitude)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    shape = RoundedCornerShape(12.dp),
                )

                OutlinedTextField(
                    textStyle = MaterialTheme.typography.bodyLarge,
                    value = latitude,
                    onValueChange = { latitude = it },
                    label = { Text(text = stringResource(id = R.string.location_dialog_latitude)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    shape = RoundedCornerShape(12.dp),
                )

                OutlinedTextField(
                    textStyle = MaterialTheme.typography.bodyLarge,
                    value = longitude,
                    onValueChange = { longitude = it },
                    label = { Text(text = stringResource(id = R.string.location_dialog_longitude)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    shape = RoundedCornerShape(12.dp),
                )

                Text(
                    modifier = Modifier
                        .padding(start = 12.dp),
                    text = stringResource(id = R.string.dialog_empty_zero)
                )
            }

        },
        shape = RoundedCornerShape(20.dp)
    )
}