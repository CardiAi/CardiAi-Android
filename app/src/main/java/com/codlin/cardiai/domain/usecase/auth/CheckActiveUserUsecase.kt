package com.codlin.cardiai.domain.usecase.auth

import com.codlin.cardiai.data.datasource.local.datastore.UserPreferences
import javax.inject.Inject

class CheckActiveUserUsecase @Inject constructor(
    private val userPreferences: UserPreferences
) {
    suspend operator fun invoke(): Boolean =
        userPreferences.isTokenAvailable()
}