package com.codlin.cardiai.domain.usecase

import com.codlin.cardiai.domain.model.User
import com.codlin.cardiai.domain.repo.UserRepo
import javax.inject.Inject

class SignupUsecase @Inject constructor(private val userRepo: UserRepo) {
    operator fun invoke (name:String, email:String, password:String):Result<User>{
        return userRepo.signup(name, email, password)
    }
}