package com.codlin.cardiai.data.datasource.remote.service

import com.codlin.cardiai.data.datasource.remote.dto.base.BaseResponse
import com.codlin.cardiai.data.datasource.remote.dto.records.RecordDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import com.codlin.cardiai.data.datasource.remote.dto.base.BasePaginationResponse
import com.codlin.cardiai.data.datasource.remote.interceptor.Authenticated
import retrofit2.http.GET


interface RecordService {
    @POST("patient/{id}/record/add")
    suspend fun addRecord(
        @Path("id") patientId: Int,
        @Body record: RecordDto,
    ): Response<BaseResponse<RecordDto>>

    @GET("patient/{id}/records")
    @Authenticated
    suspend fun getPatientRecords(@Path("id") patientId: Int):
            Response<BaseResponse<BasePaginationResponse<RecordDto>>>
}