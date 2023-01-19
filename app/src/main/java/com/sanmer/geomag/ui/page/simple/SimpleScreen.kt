package com.sanmer.geomag.ui.page.simple

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sanmer.geomag.R
import com.sanmer.geomag.data.Constant
import com.sanmer.geomag.ui.expansion.navigatePopUpTo
import com.sanmer.geomag.ui.navigation.MainGraph
import com.sanmer.geomag.ui.utils.Logo
import com.sanmer.geomag.viewmodel.HomeViewModel

@Composable
fun SimpleScreen(
    navController: NavController,
) {
    var isFirst by rememberSaveable { mutableStateOf(true) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SimpleTopBar(
                navController = navController,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            SimpleFloatingButton { if (isFirst) isFirst = false }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(all = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            InputItem()

            if (Constant.records.isNotEmpty() && !isFirst) {
                OutputItem()
            }
        }
    }
}

@Composable
private fun SimpleTopBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior
) = TopAppBar(
    title = {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.titleLarge
        )
    },
    navigationIcon = {
        Logo(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .size(32.dp),
            iconRes = R.drawable.ic_logo
        )
    },
    actions = {
        IconButton(
            onClick = {
                navController.navigatePopUpTo(MainGraph.Records.route)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.message_text_outline),
                contentDescription = null
            )
        }

        IconButton(
            onClick = {
                navController.navigatePopUpTo(MainGraph.Settings.route)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.setting_outline),
                contentDescription = null
            )
        }
    },
    scrollBehavior = scrollBehavior
)

@Composable
private fun SimpleFloatingButton(
    viewModel: HomeViewModel = viewModel(),
    onClick: () -> Unit,
) {
    if (viewModel.decimalYears in viewModel.model.start .. viewModel.model.end) {
        FloatingActionButton(
            onClick = {
                val record = viewModel.runModel()
                viewModel.toDatabase(record)
                onClick()
            },
            contentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                painter = painterResource(id = R.drawable.play_outline),
                contentDescription = null
            )
        }
    }
}