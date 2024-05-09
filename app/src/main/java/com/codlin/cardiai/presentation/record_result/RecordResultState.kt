package com.codlin.cardiai.presentation.record_result

import com.codlin.cardiai.domain.model.record.Record


data class RecordResultState(
    val record: Record,
    val navDestination: RecordResultStateDestination? = null,
)

interface RecordResultStateDestination {
    data object NewRecordDestination : RecordResultStateDestination
    data object PatientListDestination : RecordResultStateDestination
}
