package com.sanmer.geomag.ui.page.records

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sanmer.geomag.R
import com.sanmer.geomag.data.Constant
import com.sanmer.geomag.data.record.Record
import com.sanmer.geomag.ui.expansion.navigatePopUpTo
import com.sanmer.geomag.ui.navigation.RecordGraph
import com.sanmer.geomag.viewmodel.RecordViewModel

@Composable
fun RecordsScreen(
    navController: NavController,
    viewModel: RecordViewModel = viewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val list by remember {
        derivedStateOf {
            Constant.records.asReversed()
        }
    }

    if (viewModel.isEmpty()) {
        viewModel.close()
    }

    BackHandler(viewModel.chooser) {
        viewModel.close()
    }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RecordsTopBar(
                viewModel = viewModel,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        if (list.isEmpty()) {
            EmptyView(
                modifier = Modifier
                    .padding(innerPadding)
            )
        } else {
            RecordsList(
                modifier = Modifier
                    .padding(innerPadding),
                viewModel = viewModel,
                list = list,
                navController = navController
            )
        }
    }
}

@Composable
private fun RecordsTopBar(
    viewModel: RecordViewModel = viewModel(),
    scrollBehavior: TopAppBarScrollBehavior
) = if (viewModel.chooser) {
    RecordsSharedTopBar(
        viewModel = viewModel,
        scrollBehavior = scrollBehavior
    )
} else {
    RecordsNormalTopBar(
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun RecordsNormalTopBar(
    scrollBehavior: TopAppBarScrollBehavior
) = TopAppBar(
    title = {
        Text(
            text = stringResource(id = R.string.page_record),
            style = MaterialTheme.typography.titleLarge
        )
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

        IconButton(
            onClick = {
                viewModel.delete()
            }
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
    viewModel: RecordViewModel = viewModel(),
    list: MutableList<Record>,
    navController: NavController,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        itemsIndexed(list) { index, value ->
            RecordItem(
                viewModel = viewModel,
                record = value
            ) {
                navController.navigatePopUpTo("${RecordGraph.View.route}/$index")
            }
        }
    }
}

@Composable
private fun EmptyView(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(90.dp),
            alpha = 0.3f,
            painter = painterResource(id = R.drawable.box_time_outline),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.outline)
        )
    }
}