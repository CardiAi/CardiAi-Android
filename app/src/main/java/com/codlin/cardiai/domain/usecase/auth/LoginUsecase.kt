package com.codlin.cardiai.domain.usecase.auth

import com.codlin.cardiai.BuildConfig
import com.codlin.cardiai.domain.model.User
import com.codlin.cardiai.domain.repo.UserRepo
import com.codlin.cardiai.domain.util.Resource
import com.codlin.cardiai.domain.util.ValidationUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUsecase @Inject constructor(
    private val userRepo: UserRepo
) {
    operator fun invoke(email: String, password: String): Flow<Resource<User>> {
        val result = if (BuildConfig.DEBUG && email.isEmpty() && password.isEmpty()) {
            try {
                userRepo.login(BuildConfig.userEmail, BuildConfig.userPass)
            } catch (e: Exception) {
                flow {
                    emit(Resource.Error(e))
                }
            }
        } else {
            try {
                ValidationUtil.validateEmail(email)
                ValidationUtil.validatePassword(password)
                userRepo.login(email, password)
            } catch (e: Exception) {
                flow {
                    emit(Resource.Error(e))
                }
            }
        }
        return result
    }
}