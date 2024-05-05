package com.codlin.cardiai.domain.repo

import com.codlin.cardiai.domain.model.User

interface UserRepo {
    suspend fun login(email: String, password: String): Result<User>
    fun signup(name: String, email: String, password: String): Result<User>
    fun logout(): Result<Unit>
}