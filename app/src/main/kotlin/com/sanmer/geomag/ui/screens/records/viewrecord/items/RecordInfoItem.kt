package com.sanmer.geomag.ui.screens.records.viewrecord.items

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
import com.sanmer.geomag.Geomag
import com.sanmer.geomag.R
import com.sanmer.geomag.model.Record

@Composable
fun RecordInfoItem(
    record: Record,
    modifier: Modifier = Modifier
) = OutlinedCard(
    modifier = modifier,
    shape = RoundedCornerShape(15.dp)
) {
    Column(
        modifier = Modifier
            .padding(all = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ValueItem(
            key = stringResource(id = R.string.record_info_model),
            value = record.model.name
        )

        ValueItem(
            key = stringResource(id = R.string.overview_latitude),
            value = record.position.latitudeWithUnit
        )

        ValueItem(
            key = stringResource(id = R.string.overview_longitude),
            value = record.position.longitudeWithUnit
        )

        ValueItem(
            key = stringResource(id = R.string.overview_altitude),
            value = record.position.altitudeWithUnit
        )

        ValueItem(
            key = stringResource(id = R.string.overview_datetime),
            value = record.time.toString()
        )

        ValueItem(
            key = stringResource(id = R.string.overview_decimal),
            value = Geomag.toDecimalYears(record.time).toString()
        )
    }
}

@Composable
private fun ValueItem(
    key: String,
    value: String,
) = Column(
    modifier = Modifier.fillMaxWidth()
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