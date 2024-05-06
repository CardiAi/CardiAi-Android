package com.codlin.cardiai.data.datasource.remote.service

import com.codlin.cardiai.data.datasource.remote.dto.auth.AuthUserDto
import com.codlin.cardiai.data.datasource.remote.dto.auth.LoginBody
import com.codlin.cardiai.data.datasource.remote.dto.auth.SignupBody
import com.codlin.cardiai.data.datasource.remote.dto.auth.UserDto
import com.codlin.cardiai.data.datasource.remote.dto.base.BaseResponse
import com.codlin.cardiai.data.datasource.remote.interceptor.Authenticated
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @POST("login")
    suspend fun login(@Body body: LoginBody): Response<BaseResponse<AuthUserDto>>

    @POST("register")
    suspend fun signup(@Body body: SignupBody): Response<BaseResponse<String>>

    @GET("user")
    @Authenticated
    suspend fun getActiveUser(): Response<BaseResponse<UserDto>>

    @POST("logout")
    @Authenticated
    suspend fun logout(): Response<BaseResponse<String>>
}