package com.codlin.cardiai.data.datasource.remote

import com.codlin.cardiai.data.datasource.remote.dto.auth.AuthUserDto
import com.codlin.cardiai.data.datasource.remote.dto.auth.LoginBody
import com.codlin.cardiai.data.datasource.remote.dto.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(@Body body: LoginBody): BaseResponse<AuthUserDto>
}