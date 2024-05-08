package com.codlin.cardiai.presentation.home.patients_list

import com.codlin.cardiai.domain.model.Patient

data class PatientListState(
    val inSelectMode: Boolean = false,
    val selectedId: Int? = null,
    val isMenuShown: Boolean = false,
    val searchQuery: String = "",
    val isSearchVisible: Boolean = false,
    val navDestination: PatientsListDestination? = null,
)

sealed interface PatientsListDestination {
    data class PatientsListDetailsDestination(val patient: Patient) : PatientsListDestination
    data object AuthDestination : PatientsListDestination
    data object NavigateUp : PatientsListDestination
}
