package com.codlin.cardiai.presentation.home.patients_list

import com.codlin.cardiai.domain.model.Gender
import com.codlin.cardiai.domain.model.Patient

sealed class PatientListEvent {
    data class OnPatientClicked(val patient: Patient) : PatientListEvent()
    data object OnStartDiagnosisClicked : PatientListEvent()
    data object ToggleMenu : PatientListEvent()
    data object OnLogout : PatientListEvent()
    data class OnSearchQueryChange(val query: String) : PatientListEvent()
    data object InstantSearch : PatientListEvent()
    data object ToggleSearchVisibility : PatientListEvent()
    data object OnBackClicked : PatientListEvent()
    data object OnAddPatientClicked : PatientListEvent()
    data object OnBottomSheetDismissed : PatientListEvent()
    data object OnContinueClicked : PatientListEvent()
    data object OnConfirmAdd : PatientListEvent()
    data class OnAddPatient(
        val name: String? = null,
        val age: String? = null,
        val gender: Gender? = null
    ) : PatientListEvent()
}