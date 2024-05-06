package com.codlin.cardiai.presentation.home.patient_details

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.codlin.cardiai.domain.model.Patient
import com.codlin.cardiai.presentation.navigation.HomeNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@HomeNavGraph
@Destination(navArgsDelegate = Patient::class)
@Composable
fun PatientDetailsScreen(
    navigator: DestinationsNavigator,
    patient: Patient,
) {
    PatientDetailsContent(patient = patient)
}

@Composable
private fun PatientDetailsContent(patient: Patient) {
    Column {
        Text(text = patient.id.toString())
        Text(text = patient.name)
        Text(text = patient.age.toString())
        Text(text = patient.gender.name.lowercase().replaceFirstChar { it.uppercase() })
    }
}

@Preview
@Composable
private fun PatientDetailsPreview() {

}