package com.codlin.cardiai.domain.repo

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

import com.codlin.cardiai.domain.model.record.Record
import com.codlin.cardiai.domain.util.Resource

interface RecordRepo {
    fun getPatientRecords(id: Int): Flow<PagingData<Record>>

    fun newRecord(patientId: Int, record: Record): Flow<Resource<Record>>
}