package com.codlin.cardiai.presentation.home.patient_details

import com.codlin.cardiai.domain.model.Gender

sealed class PatientDetailsEvent {
    data object OnStartDiagnosisClicked : PatientDetailsEvent()
    data object OnEditClicked : PatientDetailsEvent()
    data object OnConfirmEdit : PatientDetailsEvent()
    data object OnDeleteClicked : PatientDetailsEvent()
    data object OnRecordClicked : PatientDetailsEvent()
    data object OnBackClicked : PatientDetailsEvent()
    data object OnBottomSheetDismissed : PatientDetailsEvent()
    data class OnEditPatient(
        val name: String? = null,
        val age: Int? = null,
        val gender: Gender? = null
    ) : PatientDetailsEvent()
}