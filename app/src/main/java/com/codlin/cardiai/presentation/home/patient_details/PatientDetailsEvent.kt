package com.codlin.cardiai.presentation.home.patient_details

sealed class PatientDetailsEvent {
    data object OnStartDiagnosisClicked : PatientDetailsEvent()
    data object OnEditClicked : PatientDetailsEvent()
    data object OnDeleteClicked : PatientDetailsEvent()
    data object OnCardClicked : PatientDetailsEvent()
    data object OnBackClicked : PatientDetailsEvent()
}