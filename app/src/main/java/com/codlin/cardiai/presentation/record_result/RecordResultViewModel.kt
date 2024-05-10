package com.codlin.cardiai.presentation.record_result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codlin.cardiai.domain.model.record.Record
import com.codlin.cardiai.domain.usecase.records.AddRecordUseCase
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
import timber.log.Timber

@HiltViewModel(assistedFactory = RecordResultViewModel.RecordResultViewModelFactory::class)
class RecordResultViewModel @AssistedInject constructor(
    @Assisted record: Record,
    private val addRecordUseCase: AddRecordUseCase
) : ViewModel() {

    @AssistedFactory
    interface RecordResultViewModelFactory {
        fun create(record: Record): RecordResultViewModel
    }

    private val _state = MutableStateFlow(RecordResultState(record))
    val state = _state.asStateFlow()

    init {
        Timber.tag("SendingRecord")
        sendRecordToModel(record)
    }

    private fun sendRecordToModel(record: Record) {
        Timber.d("Patient Id: ${record.patientId}")
        Timber.d("Record: $record")
        viewModelScope.launch {
            addRecordUseCase(record.patientId!!, record).collectLatest { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                screenError = resource.exception?.message
                                    ?: "An unknown error occurred. Please try again later.",
                                navDestination = RecordResultDestination.PatientListDestination
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true,
                            )
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                record = resource.data!!,
                                isLoading = false,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun navigateToPatientsList() {
        _state.update {
            it.copy(
                navDestination = RecordResultDestination.PatientListDestination
            )
        }
    }

    fun onEvent(event: RecordResultEvent) {
        when (event) {
            RecordResultEvent.OnContinueClicked -> navigateToPatientsList()
            RecordResultEvent.OnBackClicked -> navigateToPatientsList()
        }
    }

    fun resetEvents() {
        _state.update {
            it.copy(
                navDestination = null,
                screenError = null
            )
        }
    }
}