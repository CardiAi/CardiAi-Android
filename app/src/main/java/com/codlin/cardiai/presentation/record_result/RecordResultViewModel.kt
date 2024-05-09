package com.codlin.cardiai.presentation.record_result

import androidx.lifecycle.ViewModel
import com.codlin.cardiai.domain.model.record.Record
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel(assistedFactory = RecordResultViewModel.RecordResultViewModelFactory::class)
class RecordResultViewModel @AssistedInject constructor(
    @Assisted private val record: Record
) : ViewModel() {

    @AssistedFactory
    interface RecordResultViewModelFactory {
        fun create(record: Record): RecordResultViewModel
    }

    private val _state = MutableStateFlow(RecordResultState(record))
    val state = _state.asStateFlow()

    fun onEvent(event: RecordResultEvent) {
        when (event) {
            RecordResultEvent.OnContinueClicked -> TODO()
            RecordResultEvent.OnStartNewDiagnosisClicked -> TODO()
        }
    }

    fun resetEvents() {
        _state.update {
            it.copy(
                navDestination = null
            )
        }
    }
}