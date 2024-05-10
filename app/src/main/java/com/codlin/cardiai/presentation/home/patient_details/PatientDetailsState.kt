package com.codlin.cardiai.presentation.home.patient_details

import com.codlin.cardiai.domain.model.Patient
import com.codlin.cardiai.domain.model.record.Record

data class PatientDetailsState(
    val patient: Patient,
    val patientResults: List<Record>? = null,
    val screenError: String? = null,
    val isLoading: Boolean = false,
    val isBottomSheetVisible: Boolean = false,
    val editablePatient: Patient? = patient,
    val navDestination: PatientDetailsDestination? = null,
)

sealed interface PatientDetailsDestination {
    data class DiagnosisDetailsDestination(val result: Record) : PatientDetailsDestination
    data class StartDiagnosisDestination(val patientId: Int) : PatientDetailsDestination
    data object NavigateUp : PatientDetailsDestination
}
