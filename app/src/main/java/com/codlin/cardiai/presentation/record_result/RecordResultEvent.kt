package com.codlin.cardiai.presentation.record_result

sealed class RecordResultEvent {
    data object OnContinueClicked : RecordResultEvent()
    data object OnNavigateBack : RecordResultEvent()
}