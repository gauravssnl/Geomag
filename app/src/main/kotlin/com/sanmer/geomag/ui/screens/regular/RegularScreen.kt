package com.sanmer.geomag.ui.screens.regular

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
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
import com.sanmer.geomag.BuildConfig
import com.sanmer.geomag.R
import com.sanmer.geomag.ui.navigation.graph.RecordGraph.View.toRoute
import com.sanmer.geomag.ui.utils.HtmlText
import com.sanmer.geomag.ui.utils.Logo
import com.sanmer.geomag.ui.utils.navigatePopUpTo
import com.sanmer.geomag.ui.utils.none
import com.sanmer.geomag.viewmodel.HomeViewModel

@Composable
fun RegularScreen(
    navController: NavController,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val context = LocalContext.current

    BackHandler {
        val home = Intent(Intent.ACTION_MAIN).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            addCategory(Intent.CATEGORY_HOME)
        }
        context.startActivity(home)
    }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RegularTopBar(scrollBehavior = scrollBehavior)
        },
        floatingActionButton = {
            RegularFloatingButton(
                navController = navController,
            )
        },
        contentWindowInsets = WindowInsets.none
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(all = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ModelItem()
            TimeItem()
            LocationItem()
        }
    }
}

@Composable
private fun RegularTopBar(
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
        var about by remember { mutableStateOf(false) }
        if (about) AboutDialog { about = false }
        IconButton(
            onClick = { about = true }
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
                viewModel.runModel()
                navController.navigatePopUpTo(0.toRoute())
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
    onDismissRequest = onClose,
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = AlertDialogDefaults.containerColor,
        tonalElevation = AlertDialogDefaults.TonalElevation
    ) {
        Row(
            modifier = Modifier
                .padding(all = 24.dp)
        ) {
            Logo(
                modifier = Modifier
                    .size(50.dp),
                iconRes = R.drawable.ic_logo
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 18.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${BuildConfig.VERSION_NAME}(${BuildConfig.VERSION_CODE})",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                HtmlText(
                    text = stringResource(
                        id = R.string.about_source_code,
                        "<b><a href=\"https://github.com/ya0211/Geomag\">GitHub</a></b>"
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}