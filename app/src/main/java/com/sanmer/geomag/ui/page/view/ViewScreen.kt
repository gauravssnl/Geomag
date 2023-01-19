package com.sanmer.geomag.ui.page.view

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sanmer.geomag.R
import com.sanmer.geomag.data.Constant
import com.sanmer.geomag.data.json.JsonUtils
import com.sanmer.geomag.data.record.Record
import com.sanmer.geomag.ui.expansion.navigateBack
import com.sanmer.geomag.ui.utils.NavigateUpTopBar

@Composable
fun ViewScreen(
    navController: NavController,
    record: Record
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    BackHandler { navController.navigateBack() }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ViewTopBar(
                scrollBehavior = scrollBehavior,
                navController = navController,
                record = record
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
            InfoItem(record = record)
            ValuesItem(value = record.values)
        }
    }
}

@Composable
private fun ViewTopBar(
    context: Context = LocalContext.current,
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavController,
    record: Record
) = NavigateUpTopBar(
    title = R.string.page_view,
    actions = {
        IconButton(
            onClick = {
                JsonUtils.share(context = context, value = record)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.send_outline),
                contentDescription = null
            )
        }

        IconButton(
            onClick = {
                /*
                It takes 200 milliseconds for exit animation,
                delay deleting the data to ensure safe exit.
                 */
                Constant.delete(value = record, timeMillis = 400)
                navController.navigateBack()
            }
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