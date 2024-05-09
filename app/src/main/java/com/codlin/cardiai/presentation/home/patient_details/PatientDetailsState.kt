package com.codlin.cardiai.presentation.home.patient_details

import com.codlin.cardiai.domain.model.Patient
import com.codlin.cardiai.domain.model.record.Record

data class PatientDetailsState(
    val patient: Patient = Patient(),
    val patientResults: List<Record>? = null,
    val navDestination: PatientDetailsDestination? = null,
)

sealed interface PatientDetailsDestination {
    data class DiagnosisDetailsDestination(val result: Record) : PatientDetailsDestination
    data class StartDiagnosisDestination(val patientId: Int) : PatientDetailsDestination
    data object PatientListDestination : PatientDetailsDestination
}
