package com.codlin.cardiai.presentation.auth.login

import androidx.lifecycle.ViewModel
import com.codlin.cardiai.domain.usecase.LoginUsecase
import com.codlin.cardiai.domain.util.ShortPasswordException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUsecase: LoginUsecase) : ViewModel() {
    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    fun login() {
        try {
            loginUsecase(email.value, password.value)
        } catch (e: ShortPasswordException) {
            _password.value = "Short PASSWORD"
        }
    }
}