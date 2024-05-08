package com.codlin.cardiai.presentation.home.patient_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codlin.cardiai.R
import com.codlin.cardiai.domain.model.Gender
import com.codlin.cardiai.domain.model.Patient
import com.codlin.cardiai.presentation.UIFormatter
import com.codlin.cardiai.presentation.components.RecordIcon
import com.codlin.cardiai.presentation.home.patients_list.components.StartDiagnosisButton
import com.codlin.cardiai.presentation.navigation.HomeNavGraph
import com.codlin.cardiai.presentation.theme.CardiAiTheme
import com.codlin.cardiai.presentation.theme.Neutral500
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@HomeNavGraph
@Destination(navArgsDelegate = Patient::class)
@Composable
fun PatientDetailsScreen(
    viewModel: PatientDetailsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    patient: Patient,
) {
    //PatientDetailsContent(patient = patient)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PatientDetailsContent(
    patient: Patient,
    onEvent: (PatientDetailsEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { onEvent(PatientDetailsEvent.OnBackClicked) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = "Arrow Back Icon",
                        )
                    }
                },
                actions = {

                    IconButton(onClick = { onEvent(PatientDetailsEvent.OnEditClicked) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.edit_icon),
                            contentDescription = "Arrow Back Icon",
                        )
                    }
                    IconButton(onClick = { onEvent(PatientDetailsEvent.OnDeleteClicked) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.delete_icon),
                            contentDescription = "Arrow Back Icon",
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            StartDiagnosisButton({ onEvent(PatientDetailsEvent.OnStartDiagnosisClicked) })
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                RecordIcon(
                    result = patient.lastResult,
                    modifierBackground = Modifier.size(98.dp),
                    modifierHeart = Modifier.size(49.dp)
                )
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = patient.name,
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.Black,
                    )
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = "${patient.age} years old",
                            style = MaterialTheme.typography.displayMedium,
                            color = Color.Black,
                        )
                        Spacer(modifier = Modifier.width(24.dp))
                        Text(
                            text = patient.gender.toString(),
                            style = MaterialTheme.typography.displayMedium,
                            color = Color.Black,
                        )
                    }
                    Text(
                        text = UIFormatter.formatRecordResult(patient.lastResult),
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp),
                color = Neutral500,
            )
            LazyColumn {

            }
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun PatientDetailsPreview() {
    CardiAiTheme {
        PatientDetailsContent(
            patient = Patient(
                name = "Kareem Sayed",
                age = 16,
                gender = Gender.Male,
                lastResult = 2,
            ),
            onEvent = {}
        )
    }
}