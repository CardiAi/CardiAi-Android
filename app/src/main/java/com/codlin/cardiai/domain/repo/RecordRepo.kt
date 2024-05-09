package com.codlin.cardiai.domain.repo

import androidx.paging.PagingData
import com.codlin.cardiai.domain.model.Record
import kotlinx.coroutines.flow.Flow

interface RecordRepo {
    fun getPatientRecords(id: Int): Flow<PagingData<Record>>

}