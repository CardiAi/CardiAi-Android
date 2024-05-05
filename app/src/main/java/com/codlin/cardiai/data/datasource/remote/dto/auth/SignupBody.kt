package com.codlin.cardiai.data.datasource.remote.dto.auth

data class SignupBody(
    val name: String,
    val email: String,
    val password: String,
)
