package com.codlin.cardiai.data.repo

import com.codlin.cardiai.data.datasource.remote.ApiService
import com.codlin.cardiai.domain.model.User
import com.codlin.cardiai.domain.repo.UserRepo
import javax.inject.Inject

class UserRepoImpl @Inject constructor(private val apiService: ApiService) : UserRepo {
    override fun login(email: String, password: String): Result<User> {
        // Login by retrofit
        // get result and return
        return Result.success(User())
    }

    override fun signup(name: String, email: String, password: String): Result<User> {
        return Result.success(User())
    }

    override fun logout(): Result<Unit> {
        return Result.success(Unit)
    }
}