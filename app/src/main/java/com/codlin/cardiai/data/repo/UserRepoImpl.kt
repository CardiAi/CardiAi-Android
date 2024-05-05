package com.codlin.cardiai.data.repo

import android.util.Log
import com.codlin.cardiai.data.datasource.local.datastore.UserPreferences
import com.codlin.cardiai.data.datasource.remote.ApiService
import com.codlin.cardiai.data.datasource.remote.dto.auth.LoginBody
import com.codlin.cardiai.data.datasource.remote.util.tryRequest
import com.codlin.cardiai.domain.model.User
import com.codlin.cardiai.domain.repo.UserRepo
import com.codlin.cardiai.domain.util.Resource
import com.codlin.cardiai.domain.util.exception.UnauthorizedException
import com.codlin.cardiai.domain.util.exception.WrongCredentialsException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
) : UserRepo {

    override fun login(email: String, password: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        val authUser = tryRequest {
            val response = apiService.login(LoginBody(email, password))
            if (response.isSuccessful) {
                val body = response.body()!!
                body.data!!
            } else {
                emit(Resource.Error(WrongCredentialsException()))
                null
            }
        }
        authUser?.let {
            emit(Resource.Success(it.user.toDomainModel()))
            userPreferences.saveToken(it.token)
        }
    }

    override fun signup(name: String, email: String, password: String): Flow<Resource<Unit>> {
        return flow {

        }
    }

    override fun logout(): Resource<Unit> {
        return Resource.Success(Unit)
    }

    override fun getActiveUser(): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        Log.d("UserRepo", "Getting Active User")
        val user = tryRequest {
            val response = apiService.getActiveUser()
            Log.d("UserRepo", "$response")
            if (response.isSuccessful) {
                val body = response.body()!!
                Log.d("UserRepo", body.data.toString())
                body.data!!.toDomainModel()
            } else {
                emit(Resource.Error(UnauthorizedException()))
                null
            }
        }
        Log.d("UserRepo", "Got user: $user")
        emit(Resource.Success(user))
    }
}