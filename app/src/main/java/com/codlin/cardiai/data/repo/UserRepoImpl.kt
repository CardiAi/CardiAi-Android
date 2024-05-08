package com.codlin.cardiai.data.repo

import com.codlin.cardiai.data.datasource.local.datastore.UserPreferences
import com.codlin.cardiai.data.datasource.remote.dto.auth.LoginBody
import com.codlin.cardiai.data.datasource.remote.dto.auth.SignupBody
import com.codlin.cardiai.data.datasource.remote.service.AuthService
import com.codlin.cardiai.data.datasource.remote.util.tryRequest
import com.codlin.cardiai.domain.model.User
import com.codlin.cardiai.domain.repo.UserRepo
import com.codlin.cardiai.domain.util.Resource
import com.codlin.cardiai.domain.util.exception.EmailTakenException
import com.codlin.cardiai.domain.util.exception.UnauthorizedException
import com.codlin.cardiai.domain.util.exception.WrongCredentialsException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class UserRepoImpl @Inject constructor(
    private val authService: AuthService,
    private val userPreferences: UserPreferences
) : UserRepo {
    init {
        Timber.tag("UserRepo")
    }

    override fun login(email: String, password: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        val authUser = tryRequest {
            val response = authService.login(LoginBody(email, password))
            if (response.isSuccessful) {
                val body = response.body()!!
                Timber.d(body.toString())
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

    override fun signup(name: String, email: String, password: String): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading())
            val result = tryRequest {
                val response = authService.signup(SignupBody(name, email, password))
                if (response.isSuccessful) {
                    Unit
                } else {
                    Timber.e("Request Failed")
                    emit(Resource.Error(EmailTakenException()))
                    null
                }
            }
            result?.let {
                emit(Resource.Success(Unit))
            }
        }

    override suspend fun logout() {
        try {
            userPreferences.clearToken()
            authService.logout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getActiveUser(): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        Timber.d("Getting Active User")
        val user = tryRequest {
            val response = authService.getActiveUser()
            Timber.d("$response")
            if (response.isSuccessful) {
                val body = response.body()!!
                Timber.d(body.data.toString())
                body.data!!.toDomainModel()
            } else {
                emit(Resource.Error(UnauthorizedException()))
                null
            }
        }
        Timber.d("Got user: $user")
        emit(Resource.Success(user))
    }
}