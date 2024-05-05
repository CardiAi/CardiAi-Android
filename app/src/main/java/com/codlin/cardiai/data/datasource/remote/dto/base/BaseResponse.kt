package com.codlin.cardiai.data.datasource.remote.dto.base

/**
 * We have to handle UnAuthorized
 *
 * @param data could be of type
 * [List<Record>, List<Patient>, Patient, Record, AuthUser]
 */
data class BaseResponse<T>(
    val success: Boolean,
    val message: String?,
    val data: T?,
    val errors: Errors?,
)
