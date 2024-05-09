package com.codlin.cardiai.presentation.record_result

sealed class RecordResultEvents {
    data object onStartNewDiagnosisClicked:RecordResultEvents()
    data object onContinueClicked:RecordResultEvents()
}