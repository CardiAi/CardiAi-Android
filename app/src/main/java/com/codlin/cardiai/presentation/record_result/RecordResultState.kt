package com.codlin.cardiai.presentation.record_result

import com.codlin.cardiai.domain.model.Patient


data class ResultState(
    val patient: Patient = Patient(),
    val navDestination: RecordResultStateDestination? = null,
)

interface RecordResultStateDestination {
    data object NewRecordDestination : RecordResultStateDestination
    data object PatientListDestination : RecordResultStateDestination
}
