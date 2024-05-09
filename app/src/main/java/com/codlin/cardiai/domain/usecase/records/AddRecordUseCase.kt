package com.codlin.cardiai.domain.usecase.records

import com.codlin.cardiai.domain.model.record.Record
import com.codlin.cardiai.domain.repo.RecordRepo
import com.codlin.cardiai.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddRecordUseCase @Inject constructor(
    private val recordRepo: RecordRepo,
) {
    operator fun invoke(patientId: Int, record: Record): Flow<Resource<Record>> =
        recordRepo.newRecord(patientId, record)
}