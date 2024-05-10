package com.codlin.cardiai.domain.usecase.records

import androidx.paging.PagingData
import com.codlin.cardiai.domain.model.record.Record
import com.codlin.cardiai.domain.repo.RecordRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPatientRecordsUseCase @Inject constructor(private val recordRepo: RecordRepo) {
    operator fun invoke(patientId: Int): Flow<PagingData<Record>> =
        recordRepo.getPatientRecords(patientId)
}