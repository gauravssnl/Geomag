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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sanmer.geomag.R
import com.sanmer.geomag.core.models.MagneticField

@Composable
fun ValuesItem(
    value: MagneticField
) {
    OutlinedCard(
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ValueItem(
                key = stringResource(id = R.string.value_f),
                value1 = "${value.totalIntensity} nT",
                value2 = "${value.totalSV} nT/yr"
            )

            ValueItem(
                key = stringResource(id = R.string.value_h),
                value1 = "${value.horizontalIntensity} nT",
                value2 = "${value.horizontalSV} nT/yr"
            )

            ValueItem(
                key = stringResource(id = R.string.value_x),
                value1 = "${value.northComponent} nT",
                value2 = "${value.northSV} nT/yr"
            )

            ValueItem(
                key = stringResource(id = R.string.value_y),
                value1 = "${value.eastComponent} nT",
                value2 = "${value.eastSV} nT/yr"
            )

            ValueItem(
                key = stringResource(id = R.string.value_z),
                value1 = "${value.verticalComponent} nT",
                value2 = "${value.verticalSV} nT/yr"
            )

            ValueItem(
                key = stringResource(id = R.string.value_d),
                value1 = "${value.declination}º ",
                value2 = "${value.declinationSV}'/yr"
            )

            ValueItem(
                key = stringResource(id = R.string.value_i),
                value1 = "${value.inclination}º ",
                value2 = "${value.inclinationSV}'/yr"
            )
        }
    }
}

@Composable
private fun ValueItem(
    key: String,
    value1: String,
    value2: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = key,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )

        Text(
            text = value1,
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = value2,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}