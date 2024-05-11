package com.codlin.cardiai.presentation.home.patient_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.codlin.cardiai.domain.model.Patient
import com.codlin.cardiai.domain.model.record.Record
import com.codlin.cardiai.domain.usecase.patients.DeletePatientUseCase
import com.codlin.cardiai.domain.usecase.patients.EditPatientUseCase
import com.codlin.cardiai.domain.usecase.records.GetPatientRecordsUseCase
import com.codlin.cardiai.domain.util.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = PatientDetailsViewModel.PatientDetailsViewModelFactory::class)
class PatientDetailsViewModel @AssistedInject constructor(
    @Assisted patient: Patient,
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

    private val _records: MutableStateFlow<PagingData<Record>> =
        MutableStateFlow(PagingData.empty())
    val records = _records.asStateFlow()

    init {
        getPatientRecords()
    }

    private fun getPatientRecords() {
        viewModelScope.launch {
            _records.emit(PagingData.empty())
            getPatientRecordsUseCase(_state.value.patient.id!!)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect { records ->
                    _records.update {
                        records.map {
                            it.copy(
                                patientId = _state.value.patient.id,
                                patientName = _state.value.patient.name
                            )
                        }
                    }
                }
        }
    }

    fun onEvent(event: PatientDetailsEvent) {
        when (event) {
            PatientDetailsEvent.OnBackClicked -> {
                _state.update {
                    it.copy(
                        navDestination = PatientDetailsDestination.NavigateUp(_state.value.isPatientEdited)
                    )
                }
            }

            is PatientDetailsEvent.OnRecordClicked ->
                _state.update {
                    it.copy(
                        navDestination = PatientDetailsDestination.RecordDetailsDestination(event.record)
                    )
                }

            PatientDetailsEvent.OnDeleteClicked -> {
                viewModelScope.launch {
                    deletePatientUseCase(_state.value.patient.id!!).collectLatest { resource ->
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
                                        navDestination = PatientDetailsDestination.NavigateUp(true)
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
                        navDestination = PatientDetailsDestination.NewRecordDestination(_state.value.patient.id!!)
                    )
                }

            PatientDetailsEvent.OnConfirmEdit -> {
                viewModelScope.launch {
                    val editedPatient = Patient(
                        id = _state.value.patient.id,
                        name = _state.value.editablePatient.name,
                        gender = _state.value.editablePatient.gender,
                        age = _state.value.editablePatient.age,
                    )
                    editPatientUseCase(editedPatient).collectLatest { resource ->
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
                                        isBottomSheetVisible = false,
                                        isPatientEdited = true,
                                        patient = resource.data!!
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
                        editablePatient = it.editablePatient.copy(
                            name = event.name ?: it.editablePatient.name,
                            age = try {
                                event.age?.toInt() ?: it.editablePatient.age
                            } catch (_: Exception) {
                                null
                            },
                            gender = event.gender ?: it.editablePatient.gender
                        )
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