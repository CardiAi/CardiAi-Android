package com.codlin.cardiai.presentation.auth.login

import androidx.lifecycle.ViewModel
import com.codlin.cardiai.domain.usecase.LoginUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUsecase: LoginUsecase) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChange -> {
                _state.update {
                    it.copy(
                        emailValue = event.value
                    )
                }
            }

            LoginEvent.OnLoginClicked -> {
                try {
                    loginUsecase(_state.value.emailValue, _state.value.passwordValue)
                } catch (e: Exception) {

                }
                _state.update {
                    it.copy(
                        navDestination = LoginDestination.HomeDestination
                    )
                }
            }

            is LoginEvent.OnPasswordChange -> {
                _state.update {
                    it.copy(
                        passwordValue = event.value
                    )
                }
            }

            LoginEvent.OnSignupClicked -> {
                _state.update {
                    it.copy(
                        navDestination = LoginDestination.SignupDestination
                    )
                }
            }

            LoginEvent.TogglePasswordVisibility -> {
                _state.update {
                    it.copy(
                        isPasswordVisible = !it.isPasswordVisible
                    )
                }
            }
        }
    }

    fun resetEvents() {
        _state.update {
            it.copy(
                navDestination = null
            )
        }
    }
}