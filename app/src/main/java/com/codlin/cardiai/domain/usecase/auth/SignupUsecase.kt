package com.codlin.cardiai.domain.usecase.auth

import com.codlin.cardiai.domain.repo.UserRepo
import com.codlin.cardiai.domain.util.Resource
import com.codlin.cardiai.domain.util.ValidationUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignupUsecase @Inject constructor(private val userRepo: UserRepo) {
    operator fun invoke(name: String, email: String, password: String): Flow<Resource<Unit>> {
        return try {
            ValidationUtil.validateName(name)
            ValidationUtil.validateEmail(email)
            ValidationUtil.validatePassword(password)
            userRepo.signup(name, email, password)
        } catch (e: Exception) {
            flow {
                emit(Resource.Error(e))
            }
        }
    }
}