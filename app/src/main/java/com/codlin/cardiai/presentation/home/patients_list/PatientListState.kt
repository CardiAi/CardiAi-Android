package com.codlin.cardiai.presentation.home.patients_list

import com.codlin.cardiai.domain.model.Patient

data class PatientListState(
    val patient: Patient = Patient(),
    val inSelectMode: Boolean = false,
    val selectedId: Int? = null,
    val isMenuShown: Boolean = false,
    val searchQuery: String = "",
    val isSearchVisible: Boolean = false,
    val addedPatient: Patient = Patient(),
    val isBottomSheetVisible: Boolean = false,
    val screenError: String? = null,
    val isLoading: Boolean = false,
    val navDestination: PatientsListDestination? = null,
)

sealed interface PatientsListDestination {
    data class PatientsDetailsDestination(val patient: Patient) : PatientsListDestination
    data object AuthDestination : PatientsListDestination
    data class NewRecordDestination(val patientId: Int) : PatientsListDestination
    data object NavigateUp : PatientsListDestination
}
