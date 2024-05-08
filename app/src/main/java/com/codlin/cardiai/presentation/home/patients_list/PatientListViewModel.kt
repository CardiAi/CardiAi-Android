package com.codlin.cardiai.presentation.home.patients_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.codlin.cardiai.domain.model.Patient
import com.codlin.cardiai.domain.usecase.auth.LogoutUsecase
import com.codlin.cardiai.domain.usecase.patients.GetPatientsUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientListViewModel @Inject constructor(
    private val getPatientsUsecase: GetPatientsUsecase,
    private val logoutUsecase: LogoutUsecase,
) : ViewModel() {
    private val _state = MutableStateFlow(PatientListState())
    val state = _state.asStateFlow()

    private val _patients: MutableStateFlow<PagingData<Patient>> =
        MutableStateFlow(PagingData.empty())
    val patients = _patients.asStateFlow()

    private var searchJob: Job? = null

    init {
        searchPatients("", isInstant = true)
    }

    private suspend fun getPatients(query: String? = null) {
        getPatientsUsecase(query)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect { patients ->
                _patients.update {
                    patients
                }
            }
    }

    fun onEvent(event: PatientListEvent) {
        when (event) {
            PatientListEvent.OnLogout -> {
                viewModelScope.launch {
                    logoutUsecase()
                }
                _state.update {
                    it.copy(
                        navDestination = PatientsListDestination.AuthDestination
                    )
                }
            }

            is PatientListEvent.OnPatientClicked -> {
                if (state.value.inSelectMode) {
                    if (_state.value.selectedId == event.patient.id) {
                        _state.update {
                            it.copy(
                                selectedId = null
                            )
                        }
                    } else {
                        _state.update {
                            it.copy(
                                selectedId = event.patient.id
                            )
                        }
                    }
                } else {
                    _state.update {
                        it.copy(
                            navDestination = PatientsListDestination.PatientsListDetailsDestination(
                                event.patient
                            )
                        )
                    }
                }
            }

            PatientListEvent.OnStartDiagnosisClicked -> {
                _state.update {
                    it.copy(
                        inSelectMode = true,
                        isSearchVisible = true,
                    )
                }
            }

            PatientListEvent.ToggleMenu -> {
                _state.update {
                    it.copy(
                        isMenuShown = !it.isMenuShown
                    )
                }
            }

            is PatientListEvent.OnSearchQueryChange -> {
                _state.update {
                    it.copy(
                        searchQuery = event.query,
                    )
                }
                searchPatients(_state.value.searchQuery)
            }

            PatientListEvent.ToggleSearchVisibility -> {
                _state.update {
                    it.copy(
                        isSearchVisible = !it.isSearchVisible,
                        searchQuery = if (it.isSearchVisible) "" else it.searchQuery
                    )
                }
                if (!_state.value.isSearchVisible) {
                    searchPatients("", isInstant = true)
                }
            }

            is PatientListEvent.InstantSearch -> searchPatients(
                _state.value.searchQuery,
                isInstant = true
            )

            PatientListEvent.OnBackClicked -> {
                if (state.value.inSelectMode) {
                    _state.update {
                        it.copy(
                            inSelectMode = false,
                            isSearchVisible = false,
                            selectedId = null,
                        )
                    }
                } else if (state.value.isSearchVisible) {
                    _state.update {
                        it.copy(
                            isSearchVisible = false,
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            navDestination = PatientsListDestination.NavigateUp
                        )
                    }
                }
            }

            PatientListEvent.OnAddPatientClicked -> {
                TODO()
            }

            PatientListEvent.OnContinueClicked -> {
                TODO()
            }
        }
    }

    private fun searchPatients(searchQuery: String, isInstant: Boolean = false) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            if (!isInstant) {
                delay(2000)
            }
            getPatients(searchQuery)
        }
    }

    fun resetEvents() {
        _state.update {
            it.copy(
                navDestination = null,
            )
        }
    }
}