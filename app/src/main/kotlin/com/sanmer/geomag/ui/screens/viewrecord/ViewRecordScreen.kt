package com.sanmer.geomag.ui.screens.viewrecord

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sanmer.geomag.R
import com.sanmer.geomag.data.Constant
import com.sanmer.geomag.data.json.JsonUtils
import com.sanmer.geomag.data.record.Record
import com.sanmer.geomag.ui.component.PageIndicator
import com.sanmer.geomag.ui.utils.NavigateUpTopBar
import com.sanmer.geomag.utils.expansion.navigateBack

@Composable
fun ViewRecordScreen(
    navController: NavController,
    record: Record?
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val list by remember { derivedStateOf { Constant.records } }

    BackHandler { navController.navigateBack() }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ViewRecordTopBar(
                scrollBehavior = scrollBehavior,
                navController = navController,
                record = record
            )
        }
    ) { innerPadding ->
        if (list.isEmpty() || record == null) {
            PageIndicator(
                modifier = Modifier.padding(innerPadding),
                icon = R.drawable.box_time_outline,
                text = R.string.records_empty
            )
        } else {
            ViewRecord(
                modifier = Modifier.padding(innerPadding),
                record = record
            )
        }
    }
}

@Composable
private fun ViewRecord(
    record: Record,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        InfoItem(record = record)
        ValuesItem(value = record.values)
    }
}

@Composable
private fun ViewRecordTopBar(
    context: Context = LocalContext.current,
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavController,
    record: Record?
) = NavigateUpTopBar(
    title = R.string.page_view_record,
    actions = {
        IconButton(
            onClick = {
                JsonUtils.share(context = context, value = record!!)
            },
            enabled = record != null
        ) {
            Icon(
                painter = painterResource(id = R.drawable.send_outline),
                contentDescription = null
            )
        }

        var delete by remember { mutableStateOf(false) }
        if (delete) DeleteDialog(navController, record!!) { delete = false }
        IconButton(
            onClick = { delete = true },
            enabled = record != null
        ) {
            Icon(
                painter = painterResource(id = R.drawable.trash_outline),
                contentDescription = null
            )
        }
    },
    scrollBehavior = scrollBehavior,
    navController = navController
)

@Composable
private fun DeleteDialog(
    navController: NavController,
    record: Record,
    onClose: () -> Unit
) = AlertDialog(
    onDismissRequest = onClose,
    shape = RoundedCornerShape(20.dp),
    title = { Text(text = stringResource(id = R.string.record_dialog_delete_title)) },
    text = { Text(text = stringResource(id = R.string.record_dialog_delete_desc)) },
    confirmButton = {
        TextButton(
            onClick = {
                Constant.delete(value = record)
                navController.navigateBack()
                onClose()
            }
        ) {
            Text(
                text = stringResource(id = R.string.dialog_delete)
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
    },
)