package com.sanmer.geomag.ui.page.settings

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sanmer.geomag.R
import com.sanmer.geomag.app.runtime.Configure
import com.sanmer.geomag.data.Constant
import com.sanmer.geomag.data.json.JsonUtils
import com.sanmer.geomag.ui.activity.log.LogActivity
import com.sanmer.geomag.ui.component.*
import com.sanmer.geomag.ui.expansion.navigatePopUpTo
import com.sanmer.geomag.ui.navigation.SettingsGraph
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    var clearCache by remember { mutableStateOf(false) }
    if (clearCache) {
        ConfirmDialog(
            onClose = { clearCache = false },
            onConfirm = {
                Constant.deleteAll()
                JsonUtils.deleteJson(context)
            },
            title = stringResource(id = R.string.settings_clear_cache),
            text = stringResource(id = R.string.settings_clear_cache_desc)
        )
    }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SettingsTopBar(
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            NormalTitle(text = stringResource(id = R.string.settings_title_normal))
            NormalItem(
                iconRes = R.drawable.brush_outline,
                text = stringResource(id = R.string.settings_app_theme),
                subText = stringResource(id = R.string.settings_app_theme_desc)
            ) {
                navController.navigatePopUpTo(SettingsGraph.AppTheme.route)
            }
            NormalItem(
                iconRes = R.drawable.health_outline,
                text = stringResource(id = R.string.settings_log_viewer),
                subText = stringResource(id = R.string.settings_log_viewer_desc)
            ) {
                val intent = Intent(context, LogActivity::class.java)
                context.startActivity(intent)
            }

            NormalTitle(text = stringResource(id = R.string.settings_title_app))
            NormalItem(
                iconRes = R.drawable.trash_outline,
                text = stringResource(id = R.string.settings_clear_cache),
                subText = stringResource(id = R.string.settings_clear_cache_desc)
            ) {
                clearCache = true
            }

        }
    }
}

@Composable
private fun SettingsTopBar(
    scrollBehavior: TopAppBarScrollBehavior
) = TopAppBar(
    title = {
        Text(
            text = stringResource(id = R.string.page_settings),
            style = MaterialTheme.typography.titleLarge
        )
    },
    scrollBehavior = scrollBehavior
)

@Composable
private fun ConfirmDialog(
    onClose: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    text: String,
) = AlertDialog(
    shape = RoundedCornerShape(20.dp),
    onDismissRequest = onClose,
    title = { Text(text = title) },
    text = { Text(text = text) },
    confirmButton = {
        TextButton(
            onClick = {
                onConfirm()
                onClose()
            }
        ) {
            Text(
                text = stringResource(id = R.string.dialog_ok)
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
    }
)