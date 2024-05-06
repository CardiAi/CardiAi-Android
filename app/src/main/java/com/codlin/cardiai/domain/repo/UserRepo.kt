package com.codlin.cardiai.domain.repo

import com.codlin.cardiai.domain.model.User
import com.codlin.cardiai.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepo {
    fun login(email: String, password: String): Flow<Resource<User>>
    fun signup(name: String, email: String, password: String): Flow<Resource<Unit>>
    suspend fun logout()
    fun getActiveUser(): Flow<Resource<User>>
}