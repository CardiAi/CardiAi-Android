package com.codlin.cardiai.presentation.home.patient_details.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codlin.cardiai.domain.model.record.Record
import com.codlin.cardiai.presentation.UIFormatter
import com.codlin.cardiai.presentation.components.RecordIcon
import com.codlin.cardiai.presentation.theme.Neutrals1000
import com.codlin.cardiai.presentation.theme.Neutrals900

@Composable
fun RecordItem(record: Record, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(onClick = onClick)
            .then(modifier)
    ) {
        RecordIcon(
            result = record.result,
            modifierBackground = Modifier.size(64.dp)
        )
        record.createdAt?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.titleMedium,
                color = Neutrals1000,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f)
            )
            Text(
                text = UIFormatter.formatRecordResult(record.result),
                style = MaterialTheme.typography.displayMedium,
                color = Neutrals900
            )
        }
    }
}