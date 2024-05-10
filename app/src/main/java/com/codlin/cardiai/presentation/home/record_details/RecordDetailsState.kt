package com.codlin.cardiai.presentation.home.record_details

import com.codlin.cardiai.domain.model.record.Record

data class RecordDetailsState(
    val record: Record,
    val navigateUp: Boolean = false,
)