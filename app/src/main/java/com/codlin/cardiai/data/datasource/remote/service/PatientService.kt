package com.codlin.cardiai.data.datasource.remote.service

import com.codlin.cardiai.data.datasource.remote.dto.base.BasePaginationResponse
import com.codlin.cardiai.data.datasource.remote.dto.base.BaseResponse
import com.codlin.cardiai.data.datasource.remote.dto.patients.PatientDto
import com.codlin.cardiai.data.datasource.remote.interceptor.Authenticated
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PatientService {
    @GET("patients")
    @Authenticated
    suspend fun getPatients(@Query("page") page: Int, @Query("search") searchQuery: String? = null):
            Response<BaseResponse<BasePaginationResponse<PatientDto>>>
}