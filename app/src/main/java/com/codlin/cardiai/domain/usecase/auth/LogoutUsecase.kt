package com.codlin.cardiai.domain.usecase.auth

import com.codlin.cardiai.domain.repo.UserRepo
import javax.inject.Inject

class LogoutUsecase @Inject constructor(private val userRepo: UserRepo) {
    suspend operator fun invoke() = userRepo.logout()
}