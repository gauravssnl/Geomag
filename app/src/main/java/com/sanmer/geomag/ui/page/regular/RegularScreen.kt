package com.sanmer.geomag.ui.page.regular

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sanmer.geomag.BuildConfig
import com.sanmer.geomag.R
import com.sanmer.geomag.ui.expansion.navigatePopUpTo
import com.sanmer.geomag.ui.navigation.graph.RecordGraph
import com.sanmer.geomag.ui.utils.HtmlText
import com.sanmer.geomag.ui.utils.Logo
import com.sanmer.geomag.viewmodel.HomeViewModel

@Composable
fun RegularScreen(
    navController: NavController,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    var show by remember { mutableStateOf(false) }
    if (show) { AboutDialog { show = false } }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RegularTopBar(
                scrollBehavior = scrollBehavior,
                onAbout = { show = true }
            )
        },
        floatingActionButton = {
            RegularFloatingButton(
                navController = navController,
            )
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
            ModelItem()
            TimeItem()
            LocationItem()
        }
    }
}

@Composable
private fun RegularTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onAbout: () -> Unit
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
            onClick = onAbout
        ) {
            Icon(
                painter = painterResource(id = R.drawable.link_square_outline),
                contentDescription = null
            )
        }
    },
    scrollBehavior = scrollBehavior
)

@Composable
private fun RegularFloatingButton(
    navController: NavController,
    viewModel: HomeViewModel = viewModel()
) {
    if (viewModel.decimalYears in viewModel.model.start .. viewModel.model.end) {
        FloatingActionButton(
            onClick = {
                val record = viewModel.runModel()
                viewModel.toDatabase(record)
                navController.navigatePopUpTo("${RecordGraph.View.route}/0")
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

@Composable
private fun AboutDialog(
    onClose: () -> Unit
) = AlertDialog(
    shape = RoundedCornerShape(20.dp),
    onDismissRequest = onClose,
    text = {
        Row {
            Logo(
                modifier = Modifier
                    .size(50.dp),
                iconRes = R.drawable.ic_logo
            )

            Spacer(modifier = Modifier.width(18.dp))

            Column {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${BuildConfig.VERSION_NAME}(${BuildConfig.VERSION_CODE})",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(12.dp))

                HtmlText(
                    text = stringResource(
                        id = R.string.about_source_code,
                        "<b><a href=\"https://github.com/ya0211/Geomag\">GitHub</a></b>"
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    },
    confirmButton = {}
)