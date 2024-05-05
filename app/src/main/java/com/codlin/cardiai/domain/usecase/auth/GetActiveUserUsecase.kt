package com.codlin.cardiai.domain.usecase.auth

import com.codlin.cardiai.domain.model.User
import com.codlin.cardiai.domain.repo.UserRepo
import com.codlin.cardiai.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActiveUserUsecase @Inject constructor(
    private val userRepo: UserRepo
) {
    operator fun invoke(): Flow<Resource<User>> {
        return userRepo.getActiveUser()
    }
}