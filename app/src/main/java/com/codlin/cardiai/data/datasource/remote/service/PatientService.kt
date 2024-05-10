package com.codlin.cardiai.data.datasource.remote.service

import com.codlin.cardiai.data.datasource.remote.dto.base.BasePaginationResponse
import com.codlin.cardiai.data.datasource.remote.dto.base.BaseResponse
import com.codlin.cardiai.data.datasource.remote.dto.patients.PatientDto
import com.codlin.cardiai.data.datasource.remote.interceptor.Authenticated
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PatientService {
    @GET("patients")
    @Authenticated
    suspend fun getPatients(@Query("page") page: Int, @Query("search") searchQuery: String? = null):
            Response<BaseResponse<BasePaginationResponse<PatientDto>>>

    @PUT("patient/edit/{id}")
    @Authenticated
    suspend fun editPatient(
        @Path("id") patientId: Int, @Body patient: PatientDto
    ): Response<BaseResponse<PatientDto>>

    @POST("patient/add")
    @Authenticated
    suspend fun addPatient(@Body patient: PatientDto): Response<BaseResponse<PatientDto>>

    @DELETE("patient/delete/{id}")
    @Authenticated
    suspend fun deletePatient(@Path("id") patientId: Int): Response<BaseResponse<String>>
}