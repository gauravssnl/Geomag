package com.sanmer.geomag.ui.screens.records

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sanmer.geomag.R
import com.sanmer.geomag.app.Config
import com.sanmer.geomag.data.Constant
import com.sanmer.geomag.data.record.Record
import com.sanmer.geomag.ui.component.PageIndicator
import com.sanmer.geomag.ui.navigation.graph.RecordGraph.View.toRoute
import com.sanmer.geomag.ui.navigation.navigateToHome
import com.sanmer.geomag.utils.expansion.navigatePopUpTo
import com.sanmer.geomag.viewmodel.RecordViewModel

@Composable
fun RecordsScreen(
    navController: NavController,
    viewModel: RecordViewModel = viewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val list by remember { derivedStateOf { Constant.records } }

    if (viewModel.isEmpty()) {
        viewModel.close()
    }

    BackHandler {
        if (viewModel.chooser) {
            viewModel.close()
        } else {
            navController.navigateToHome()
        }
    }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RecordsTopBar(
                navController = navController,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        if (list.isEmpty()) {
            PageIndicator(
                modifier = Modifier.padding(innerPadding),
                icon = R.drawable.box_time_outline,
                text = R.string.records_empty
            )
        } else {
            RecordsList(
                modifier = Modifier.padding(innerPadding),
                list = list,
                navController = navController
            )
        }
    }
}

@Composable
private fun RecordsTopBar(
    viewModel: RecordViewModel = viewModel(),
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior
) = if (viewModel.chooser) {
    RecordsSharedTopBar(
        scrollBehavior = scrollBehavior
    )
} else {
    RecordsNormalTopBar(
        navController = navController,
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun RecordsNormalTopBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior
) = TopAppBar(
    title = {
        Text(
            text = stringResource(id = R.string.page_record),
            style = MaterialTheme.typography.titleLarge
        )
    },
    navigationIcon = {
        if (Config.SIMPLE_MODE) {
            IconButton(
                onClick = {
                    navController.navigateToHome()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_square_left_outline),
                    contentDescription = null
                )
            }
        }
    },
    actions = {
        IconButton(
            onClick = {
                Constant.getAll()
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.rotate_left_outline),
                contentDescription = null
            )
        }
    },
    scrollBehavior = scrollBehavior
)

@Composable
private fun RecordsSharedTopBar(
    context: Context = LocalContext.current,
    viewModel: RecordViewModel = viewModel(),
    scrollBehavior: TopAppBarScrollBehavior
) = TopAppBar(
    navigationIcon = {
        IconButton(
            onClick = {
                viewModel.close()
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.close_square_outline),
                contentDescription = null
            )
        }
    },
    title = { Text(text = "${viewModel.size}") },
    actions = {
        IconButton(
            onClick = {
                viewModel.share(context = context)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.send_outline),
                contentDescription = null
            )
        }

        var delete by remember { mutableStateOf(false) }
        if (delete) DeleteDialog { delete = false }
        IconButton(
            onClick = { delete = true }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.trash_outline),
                contentDescription = null
            )
        }
    },
    scrollBehavior = scrollBehavior
)

@Composable
private fun RecordsList(
    modifier: Modifier = Modifier,
    list: MutableList<Record>,
    navController: NavController,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        itemsIndexed(list) { index, value ->
            RecordItem(
                record = value
            ) {
                navController.navigatePopUpTo(index.toRoute())
            }
        }
    }
}

@Composable
private fun DeleteDialog(
    viewModel: RecordViewModel = viewModel(),
    onClose: () -> Unit
) = AlertDialog(
    onDismissRequest = onClose,
    shape = RoundedCornerShape(20.dp),
    title = { Text(text = stringResource(id = R.string.records_dialog_delete_title)) },
    text = { Text(text = stringResource(id = R.string.records_dialog_delete_desc)) },
    confirmButton = {
        TextButton(
            onClick = {
                viewModel.delete()
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