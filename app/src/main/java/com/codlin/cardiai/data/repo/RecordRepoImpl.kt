package com.codlin.cardiai.data.repo

import androidx.paging.PagingData
import com.codlin.cardiai.domain.model.Record
import com.codlin.cardiai.domain.repo.RecordRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecordRepoImpl @Inject constructor() : RecordRepo {
    override fun getPatientRecords(id: Int): Flow<PagingData<Record>> {
        TODO()
    }
}