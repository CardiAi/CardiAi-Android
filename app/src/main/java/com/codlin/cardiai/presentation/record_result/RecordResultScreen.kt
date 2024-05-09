package com.codlin.cardiai.presentation.record_result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codlin.cardiai.domain.model.Patient
import com.codlin.cardiai.presentation.UIFormatter
import com.codlin.cardiai.presentation.components.RecordIcon
import com.codlin.cardiai.presentation.components.ThemeButton
import com.codlin.cardiai.presentation.navigation.HomeNavGraph
import com.codlin.cardiai.presentation.theme.CardiAiTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@HomeNavGraph
@Destination
@Composable
fun RecordResultScreen(
    viewModel: RecordResultViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {

}

@Composable
private fun RecordResultContent(
    patient: Patient,
    onEvent: (RecordResultEvents) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = patient.name,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 24.dp)
        )
        RecordIcon(
            result = patient.lastResult,
            modifierBackground = Modifier.size(160.dp),
            modifierHeart = Modifier.size(92.dp)
        )
        Text(
            text = UIFormatter.formatRecordResult(patient.lastResult),
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 24.dp),
        )
        Spacer(modifier = Modifier.height(170.dp))
        ThemeButton(
            text = "Continue",
            onClick = { onEvent(RecordResultEvents.onContinueClicked) }
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun RecordResultScreenPreview() {
    CardiAiTheme {
        RecordResultContent(
            patient = Patient(
                name = "Kareem Sayed",
                lastResult = 2,
            ),
            onEvent = {}
        )
    }
}