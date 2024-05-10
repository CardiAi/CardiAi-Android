package com.codlin.cardiai.presentation.record_result

import com.codlin.cardiai.domain.model.record.Record


data class RecordResultState(
    val record: Record,
    val isLoading: Boolean = true,
    val screenError: String? = null,
    val navDestination: RecordResultDestination? = null,
)

sealed interface RecordResultDestination {
    data object PatientListDestination : RecordResultDestination
}
