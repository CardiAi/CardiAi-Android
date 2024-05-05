package com.codlin.cardiai.data.datasource.remote.dto.auth

data class AuthUserDto(
    val user: UserDto,
    val token: String,
)
