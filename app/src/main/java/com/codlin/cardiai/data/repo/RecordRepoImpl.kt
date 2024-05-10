package com.codlin.cardiai.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.codlin.cardiai.data.datasource.remote.dto.records.RecordDto
import com.codlin.cardiai.data.datasource.remote.paging_datasource.RecordPagingSource
import com.codlin.cardiai.data.datasource.remote.service.RecordService
import com.codlin.cardiai.data.datasource.remote.util.tryRequest
import com.codlin.cardiai.domain.model.record.Record
import com.codlin.cardiai.domain.repo.RecordRepo
import com.codlin.cardiai.domain.util.Resource
import com.codlin.cardiai.domain.util.exception.InvalidRecordException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecordRepoImpl @Inject constructor(
    private val recordService: RecordService
) : RecordRepo {
    override fun getPatientRecords(patinetId: Int): Flow<PagingData<Record>> =
        Pager(
            config = PagingConfig(pageSize = 10, prefetchDistance = 2),
            pagingSourceFactory = {
                RecordPagingSource(recordService, patinetId)
            }
        ).flow

    override fun newRecord(patientId: Int, record: Record): Flow<Resource<Record>> = flow {
        emit(Resource.Loading())
        val resultRecord: Record? = tryRequest {
            val response = recordService.addRecord(patientId, RecordDto.fromDomainModel(record))
            if (response.isSuccessful) {
                val body = response.body()!!
                if (body.success) {
                    body.data!!.toDomainModel()
                } else {
                    emit(
                        Resource.Error(
                            InvalidRecordException(
                                body.message ?: "The record you submitted is incorrect."
                            )
                        )
                    )
                    null
                }
            } else {
                emit(Resource.Error(InvalidRecordException("The record you submitted is incorrect.")))
                null
            }
        }
        resultRecord?.let {
            emit(Resource.Success(it))
        }
    }

}