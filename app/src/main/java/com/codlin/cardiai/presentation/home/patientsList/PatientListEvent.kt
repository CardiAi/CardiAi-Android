package com.codlin.cardiai.presentation.home.patientsList

sealed class PatientListEvent {
    data object OnPatientClicked : PatientListEvent()
    data object OnStartDiagnosisClicked : PatientListEvent()
}