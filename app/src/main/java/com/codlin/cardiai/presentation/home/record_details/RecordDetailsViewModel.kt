package com.codlin.cardiai.presentation.home.record_details

import androidx.lifecycle.ViewModel
import com.codlin.cardiai.domain.model.record.Record
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel(assistedFactory = RecordDetailsViewModel.RecordDetailsViewModelFactory::class)
class RecordDetailsViewModel @AssistedInject constructor(
    @Assisted record: Record
) : ViewModel() {

    private val _state = MutableStateFlow(RecordDetailsState(record))
    val state = _state.asStateFlow()

    @AssistedFactory
    interface RecordDetailsViewModelFactory {
        fun create(record: Record): RecordDetailsViewModel
    }

    fun onEvent(event: RecordDetailsEvent) {
        when (event) {
            RecordDetailsEvent.OnBackClicked -> _state.update {
                it.copy(
                    navigateUp = true,
                )
            }
        }
    }

    fun resetEvents() {
        _state.update {
            it.copy(
                navigateUp = false,
            )
        }
    }
}