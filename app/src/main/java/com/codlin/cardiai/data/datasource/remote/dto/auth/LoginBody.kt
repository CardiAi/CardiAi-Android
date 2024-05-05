package com.codlin.cardiai.data.datasource.remote.dto.auth

data class LoginBody(
    val email: String,
    val password: String,
)