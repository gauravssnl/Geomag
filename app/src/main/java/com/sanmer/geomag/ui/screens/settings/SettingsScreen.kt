package com.sanmer.geomag.ui.screens.settings

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.sanmer.geomag.R
import com.sanmer.geomag.app.Config
import com.sanmer.geomag.app.Config.State
import com.sanmer.geomag.ui.activity.log.LogActivity
import com.sanmer.geomag.ui.component.NormalItemForSetting
import com.sanmer.geomag.ui.component.SwitchItem
import com.sanmer.geomag.ui.component.TitleItemForSetting
import com.sanmer.geomag.ui.navigation.graph.SettingsGraph
import com.sanmer.geomag.ui.navigation.navigateToHome
import com.sanmer.geomag.utils.expansion.navigatePopUpTo

@Composable
fun SettingsScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    BackHandler { navController.navigateToHome() }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SettingsTopBar(
                navController = navController,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            TitleItemForSetting(text = stringResource(id = R.string.settings_title_normal))
            NormalItemForSetting(
                iconRes = R.drawable.brush_outline,
                text = stringResource(id = R.string.settings_app_theme),
                subText = stringResource(id = R.string.settings_app_theme_desc)
            ) {
                navController.navigatePopUpTo(SettingsGraph.AppTheme.route)
            }
            NormalItemForSetting(
                iconRes = R.drawable.health_outline,
                text = stringResource(id = R.string.settings_log_viewer),
                subText = stringResource(id = R.string.settings_log_viewer_desc)
            ) {
                val intent = Intent(context, LogActivity::class.java)
                context.startActivity(intent)
            }
            TitleItemForSetting(text = stringResource(id = R.string.settings_title_app))
            SwitchItem(
                iconRes = R.drawable.main_component_outline,
                text = stringResource(id = R.string.settings_simple_mode),
                subText = stringResource(id = R.string.settings_simple_mode_desc),
                checked = State.simpleMode
            ) {
                State.simpleMode = it
            }
        }
    }
}

@Composable
private fun SettingsTopBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior
) = TopAppBar(
    title = {
        Text(
            text = stringResource(id = R.string.page_settings),
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
    scrollBehavior = scrollBehavior
)