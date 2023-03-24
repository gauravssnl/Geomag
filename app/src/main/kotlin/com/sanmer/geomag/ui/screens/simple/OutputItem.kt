package com.sanmer.geomag.ui.screens.simple

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sanmer.geomag.R
import com.sanmer.geomag.utils.JsonUtils
import com.sanmer.geomag.ui.component.CardItem
import com.sanmer.geomag.ui.screens.viewrecord.ValuesItem
import com.sanmer.geomag.viewmodel.HomeViewModel

@Composable
fun OutputItem(
    viewModel: HomeViewModel = viewModel()
) {
    val context = LocalContext.current
    val record = viewModel.record!!

    CardItem(
        iconRes = R.drawable.code_outline,
        label = stringResource(id = R.string.simple_output),
        trailingIcon = {
            IconButton(
                onClick = {
                    JsonUtils.share(context = context, value = record)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.send_outline),
                    contentDescription = null
                )
            }
        }
    ) {
        ValuesItem(
            value = record.values
        )
    }
}