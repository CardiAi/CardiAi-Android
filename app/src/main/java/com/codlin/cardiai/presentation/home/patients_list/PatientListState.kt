package com.codlin.cardiai.presentation.home.patients_list

import com.codlin.cardiai.domain.model.Patient

data class PatientListState(
    val isMenuShown: Boolean = false,
    val searchQuery: String = "",
    val isSearchVisible: Boolean = false,
    val navDestination: PatientDestination? = null,
)

sealed interface PatientDestination {
    data class PatientDetailsDestination(val patient: Patient) : PatientDestination
    data object StartDiagnosisDestination : PatientDestination
    data object AuthDestination : PatientDestination
}
