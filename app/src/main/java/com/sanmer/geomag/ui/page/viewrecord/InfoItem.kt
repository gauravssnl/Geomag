package com.sanmer.geomag.ui.page.viewrecord

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sanmer.geomag.R
import com.sanmer.geomag.core.models.Geomag
import com.sanmer.geomag.data.record.Record

@Composable
fun InfoItem(
    record: Record
) {
    OutlinedCard(
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ValueItem(
                key = stringResource(id = R.string.info_model),
                value = record.model
            )

            ValueItem(
                key = stringResource(id = R.string.info_altitude),
                value = "${record.location.altitude} km"
            )

            ValueItem(
                key = stringResource(id = R.string.info_latitude),
                value = "${record.location.latitude}º N"
            )

            ValueItem(
                key = stringResource(id = R.string.info_longitude),
                value = "${record.location.longitude}º W"
            )

            ValueItem(
                key = stringResource(id = R.string.info_time),
                value = "${record.time}"
            )

            ValueItem(
                key = stringResource(id = R.string.info_decimal),
                value = "${Geomag.toDecimalYears(record.time)}"
            )
        }
    }
}

@Composable
private fun ValueItem(
    key: String,
    value: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = key,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}