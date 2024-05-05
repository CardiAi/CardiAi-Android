package com.codlin.cardiai.data.datasource.remote.dto.auth

import com.codlin.cardiai.domain.model.User

data class UserDto(
    val id: Int,
    val name: String,
    val email: String,
) {
    fun toDomainModel(): User =
        User(
            id = id,
            name = name,
            email = email,
        )
}
