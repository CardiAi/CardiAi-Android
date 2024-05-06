package com.codlin.cardiai.presentation.home.patients_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codlin.cardiai.domain.usecase.auth.LogoutUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientListViewModel @Inject constructor(
    private val logoutUsecase: LogoutUsecase
) : ViewModel() {
    private val _state = MutableStateFlow(PatientListState())
    val state = _state.asStateFlow()

    fun onEvent(event: PatientListEvent) {
        when (event) {
            PatientListEvent.OnLogout -> {
                viewModelScope.launch {
                    logoutUsecase()
                }
                _state.update {
                    it.copy(
                        navDestination = PatientDestination.AuthDestination
                    )
                }
            }

            is PatientListEvent.OnPatientClicked -> {
                _state.update {
                    it.copy(
                        navDestination = PatientDestination.PatientDetailsDestination(event.patient)
                    )
                }
            }

            PatientListEvent.OnStartDiagnosisClicked -> {
                TODO()
            }

            PatientListEvent.ToggleMenu -> {
                _state.update {
                    it.copy(
                        isMenuShown = !it.isMenuShown
                    )
                }
            }
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