package com.codlin.cardiai.presentation.home.patientsList.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codlin.cardiai.domain.model.Patient
import com.codlin.cardiai.presentation.components.RecordIcon
import com.codlin.cardiai.presentation.theme.CardiAiTheme
import com.codlin.cardiai.presentation.theme.Neutrals1000
import com.codlin.cardiai.presentation.theme.Neutrals800
import com.codlin.cardiai.presentation.theme.Neutrals900

@Composable
fun PatientItem(patient: Patient, modifier: Modifier = Modifier) {

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RecordIcon(
            result = patient.lastResult,
            modifier = Modifier
                .size(64.dp),
        )
        Spacer(modifier = Modifier.width(16.dp))

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = patient.name,
                style = MaterialTheme.typography.bodyMedium,
                color = Neutrals1000,
            )

            Text(
                text = patient.lastResult.toString(),
                style = MaterialTheme.typography.bodySmall,
                color = Neutrals900,
            )
        }
        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = patient.lastRecordDate.toString(),
            style = MaterialTheme.typography.displayMedium,
            color = Neutrals800,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PatientItemPreview() {
    CardiAiTheme {
        PatientItem(
            patient = Patient(
                lastResult = 2,
                name = "Zaly Mario",
                lastRecordDate = "6/5/2024"
            )
        )
    }
}