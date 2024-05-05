package com.codlin.cardiai.data.repo

import android.util.Log
import com.codlin.cardiai.data.datasource.remote.ApiService
import com.codlin.cardiai.data.datasource.remote.dto.auth.LoginBody
import com.codlin.cardiai.domain.model.User
import com.codlin.cardiai.domain.repo.UserRepo
import javax.inject.Inject

class UserRepoImpl @Inject constructor(private val apiService: ApiService) : UserRepo {
    override suspend fun login(email: String, password: String): Result<User> {
        val response = apiService.login(LoginBody(email, password))
        Log.d("UserRepo", "login: $response")
        return if (response.success) {
            Result.success(User())
        } else {
            Result.failure(Exception(response.message))
        }
    }

    override fun signup(name: String, email: String, password: String): Result<User> {
        return Result.success(User())
    }

    override fun logout(): Result<Unit> {
        return Result.success(Unit)
    }
}