package com.codlin.cardiai.presentation.home.patients_list

import com.codlin.cardiai.domain.model.Patient

sealed class PatientListEvent {
    data class OnPatientClicked(val patient: Patient) : PatientListEvent()
    data object OnStartDiagnosisClicked : PatientListEvent()
    data object ToggleMenu : PatientListEvent()
    data object OnLogout : PatientListEvent()
}