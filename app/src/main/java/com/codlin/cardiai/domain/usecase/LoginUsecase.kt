package com.codlin.cardiai.domain.usecase

import com.codlin.cardiai.domain.model.User
import com.codlin.cardiai.domain.repo.UserRepo
import com.codlin.cardiai.domain.util.ShortPasswordException
import javax.inject.Inject

class LoginUsecase @Inject constructor(private val userRepo: UserRepo) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        if (password.length < 6) {
            throw ShortPasswordException()
        }
        return userRepo.login(email, password)
    }
}