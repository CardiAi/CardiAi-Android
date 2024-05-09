package com.codlin.cardiai.presentation.record_result

sealed class RecordResultEvent {
    data object OnStartNewDiagnosisClicked : RecordResultEvent()
    data object OnContinueClicked : RecordResultEvent()
}