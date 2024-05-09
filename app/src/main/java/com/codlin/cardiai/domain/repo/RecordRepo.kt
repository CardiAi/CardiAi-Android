package com.codlin.cardiai.domain.repo

import com.codlin.cardiai.domain.model.record.Record
import com.codlin.cardiai.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface RecordRepo {
    fun newRecord(patientId: Int, record: Record): Flow<Resource<Record>>
}