package com.codlin.cardiai.data.datasource.remote.service

import com.codlin.cardiai.data.datasource.remote.dto.base.BasePaginationResponse
import com.codlin.cardiai.data.datasource.remote.dto.base.BaseResponse
import com.codlin.cardiai.data.datasource.remote.dto.records.RecordDto
import com.codlin.cardiai.data.datasource.remote.interceptor.Authenticated
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RecordService {
    @GET("patient/{id}/records")
    @Authenticated
    suspend fun getPatientRecords(@Path("id") patientId: Int):
            Response<BaseResponse<BasePaginationResponse<RecordDto>>>
}