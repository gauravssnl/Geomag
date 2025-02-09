package com.sanmer.geomag.ui.screens.home.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sanmer.geomag.R
import com.sanmer.geomag.ui.navigation.navigateToRecords

@Composable
fun RecordsItem(
    enableRecords: Boolean,
    navController: NavController,
    modifier: Modifier = Modifier,
    setEnableRecords: (Boolean) -> Unit,
    openBottomSheet: () -> Unit
) = OverviewCard(
    expanded = true,
    modifier = modifier,
    icon = R.drawable.note_outline,
    label = stringResource(id = R.string.page_records),
    trailingIcon = {
        Switch(
            checked = enableRecords,
            onCheckedChange = setEnableRecords
        )
    }
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        OverviewButton(
            onClick = openBottomSheet,
            icon = R.drawable.presention_chart_bold,
            text = stringResource(id = R.string.overview_current_mf)
        )

        OverviewButton(
            onClick = { navController.navigateToRecords() },
            icon = R.drawable.note_bold,
            text = stringResource(id = R.string.overview_view_records)
        )
    }
}