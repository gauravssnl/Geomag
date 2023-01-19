package com.sanmer.geomag.ui.page.apptheme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sanmer.geomag.R
import com.sanmer.geomag.ui.utils.Logo

@Composable
fun ExampleItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedCard(
            modifier = Modifier
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Logo(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .size(20.dp),
                        iconRes = R.drawable.ic_logo
                    )

                    Text(text = stringResource(id = R.string.app_name))
                }

                Surface(
                    shape = RoundedCornerShape(15.dp),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 1.dp
                ) {
                    Spacer(
                        modifier = Modifier
                            .height(60.dp)
                            .fillMaxWidth(0.9f)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(15.dp),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 1.dp
                ) {
                    Spacer(
                        modifier = Modifier
                            .height(60.dp)
                            .fillMaxWidth(0.9f)
                    )
                }

                Spacer(modifier = Modifier.height(120.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.End
                ) {
                    Spacer(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                color = MaterialTheme.colorScheme.primary
                            )
                            .size(35.dp)
                    )
                }

                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 2.dp
                ) {
                    Spacer(
                        modifier = Modifier
                            .height(45.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}