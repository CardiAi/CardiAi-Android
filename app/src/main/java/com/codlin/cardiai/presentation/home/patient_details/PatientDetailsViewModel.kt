package com.codlin.cardiai.presentation.home.patient_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codlin.cardiai.domain.model.Patient
import com.codlin.cardiai.domain.usecase.patients.DeletePatientUseCase
import com.codlin.cardiai.domain.usecase.patients.EditPatientUseCase
import com.codlin.cardiai.domain.usecase.patients.GetPatientRecordsUseCase
import com.codlin.cardiai.domain.util.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = PatientDetailsViewModel.PatientDetailsViewModelFactory::class)
class PatientDetailsViewModel @AssistedInject constructor(
    @Assisted private val patient: Patient,
    private val getPatientRecordsUseCase: GetPatientRecordsUseCase,
    private val deletePatientUseCase: DeletePatientUseCase,
    private val editPatientUseCase: EditPatientUseCase,
) : ViewModel() {

    @AssistedFactory
    interface PatientDetailsViewModelFactory {
        fun create(patient: Patient): PatientDetailsViewModel
    }

    private val _state = MutableStateFlow(PatientDetailsState(patient))
    val state = _state.asStateFlow()

    fun onEvent(event: PatientDetailsEvent) {
        when (event) {
            PatientDetailsEvent.OnBackClicked -> {
                _state.update {
                    it.copy(
                        navDestination = PatientDetailsDestination.NavigateUp
                    )
                }
            }

            PatientDetailsEvent.OnRecordClicked ->
                _state.update {
                    TODO()
//                    it.copy(
//                        navDestination = PatientDetailsDestination.DiagnosisDetailsDestination()
//                    )
                }

            PatientDetailsEvent.OnDeleteClicked -> {
                viewModelScope.launch {
                    deletePatientUseCase(patient.id!!).collectLatest { resource ->
                        when (resource) {
                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        screenError = resource.exception?.message
                                            ?: "Unknown error occurred."
                                    )
                                }
                            }

                            is Resource.Loading -> _state.update {
                                it.copy(
                                    isLoading = true,
                                )
                            }

                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        navDestination = PatientDetailsDestination.NavigateUp
                                    )
                                }
                            }
                        }
                    }
                }

            }

            PatientDetailsEvent.OnEditClicked ->
                _state.update {
                    it.copy(
                        isBottomSheetVisible = true,
                    )
                }

            PatientDetailsEvent.OnStartDiagnosisClicked ->
                _state.update {
                    it.copy(
                        navDestination = PatientDetailsDestination.StartDiagnosisDestination(patient.id!!)
                    )
                }

            PatientDetailsEvent.OnConfirmEdit -> {
                viewModelScope.launch {
                    editPatientUseCase(_state.value.editablePatient!!).collectLatest { resource ->
                        when (resource) {
                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        screenError = resource.exception?.message
                                            ?: "Unknown error occurred."
                                    )
                                }
                            }

                            is Resource.Loading ->
                                _state.update {
                                    it.copy(
                                        isLoading = true
                                    )
                                }

                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        isBottomSheetVisible = true
                                    )
                                }
                            }
                        }
                    }
                }
            }

            PatientDetailsEvent.OnBottomSheetDismissed -> {
                _state.update {
                    it.copy(
                        isBottomSheetVisible = false,
                    )
                }
            }

            is PatientDetailsEvent.OnEditPatient -> {
                _state.update {
                    it.copy(
                        editablePatient = _state.value.editablePatient?.copy(
                            name = event.name,
                            age = event.age,
                            gender = event.gender
                        )
                            ?: Patient(name = event.name, age = event.age, gender = event.gender)
                    )
                }
            }
        }
    }

    fun resetEvents() {
        _state.update {
            it.copy(
                navDestination = null,
                screenError = null,
            )
        }
    }
}