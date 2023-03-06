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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sanmer.geomag.R
import com.sanmer.geomag.data.Constant
import com.sanmer.geomag.data.json.JsonUtils
import com.sanmer.geomag.data.record.Record
import com.sanmer.geomag.ui.component.PageIndicator
import com.sanmer.geomag.ui.utils.NavigateUpTopBar
import com.sanmer.geomag.utils.expansion.navigateBack
import com.sanmer.geomag.viewmodel.DetailViewModel
import com.sanmer.geomag.viewmodel.RecordsViewModel

@Composable
fun ViewRecordScreen(
    viewModel: DetailViewModel = viewModel(),
    navController: NavController,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    BackHandler { navController.navigateBack() }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ViewRecordTopBar(
                scrollBehavior = scrollBehavior,
                navController = navController
            )
        }
    ) { innerPadding ->
        if (viewModel.record == null) {
            PageIndicator(
                modifier = Modifier.padding(innerPadding),
                icon = R.drawable.box_time_outline,
                text = R.string.records_empty
            )
        } else {
            ViewRecord(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
private fun ViewRecord(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel()
) {
    val record = viewModel.record!!

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
    viewModel: DetailViewModel = viewModel(),
    context: Context = LocalContext.current,
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavController
) = NavigateUpTopBar(
    title = R.string.page_view_record,
    actions = {
        IconButton(
            onClick = {
                JsonUtils.share(context = context, value = viewModel.record!!)
            },
            enabled = viewModel.record != null
        ) {
            Icon(
                painter = painterResource(id = R.drawable.send_outline),
                contentDescription = null
            )
        }

        var delete by remember { mutableStateOf(false) }
        if (delete) DeleteDialog(navController = navController) { delete = false }
        IconButton(
            onClick = { delete = true },
            enabled = viewModel.record != null
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
    viewModel: DetailViewModel = viewModel(),
    navController: NavController,
    onClose: () -> Unit
) = AlertDialog(
    onDismissRequest = onClose,
    shape = RoundedCornerShape(20.dp),
    title = { Text(text = stringResource(id = R.string.record_dialog_delete_title)) },
    text = { Text(text = stringResource(id = R.string.record_dialog_delete_desc)) },
    confirmButton = {
        TextButton(
            onClick = {
                viewModel.delete()
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