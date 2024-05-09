package com.codlin.cardiai.data.datasource.remote.service

import com.codlin.cardiai.data.datasource.remote.dto.base.BaseResponse
import com.codlin.cardiai.data.datasource.remote.dto.records.RecordDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface RecordService {
    @POST("patient/{id}/record/add")
    suspend fun addRecord(
        @Path("id") patientId: Int,
        @Body record: RecordDto,
    ): Response<BaseResponse<RecordDto>>
}