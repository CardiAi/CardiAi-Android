package com.codlin.cardiai.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codlin.cardiai.domain.usecase.auth.LoginUsecase
import com.codlin.cardiai.domain.util.Resource
import com.codlin.cardiai.domain.util.exception.BlankPasswordException
import com.codlin.cardiai.domain.util.exception.CantConnectException
import com.codlin.cardiai.domain.util.exception.InvalidEmailException
import com.codlin.cardiai.domain.util.exception.ShortPasswordException
import com.codlin.cardiai.domain.util.exception.WrongCredentialsException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
                        emailValue = event.value,
                        emailError = null
                    )
                }
            }

            LoginEvent.OnLoginClicked -> {
                handleLogin()
            }

            is LoginEvent.OnPasswordChange -> {
                _state.update {
                    it.copy(
                        passwordValue = event.value,
                        passwordError = null
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

    private fun handleLogin() {
        viewModelScope.launch {
            loginUsecase(
                _state.value.emailValue.trim(),
                _state.value.passwordValue.trim()
            ).collectLatest { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        when (resource.exception) {
                            is InvalidEmailException -> _state.update {
                                it.copy(
                                    emailError = "Please enter a valid email."
                                )
                            }

                            is BlankPasswordException -> _state.update {
                                it.copy(
                                    passwordError = "Password field is required."
                                )
                            }

                            is ShortPasswordException -> _state.update {
                                it.copy(
                                    passwordError = "Password is too short."
                                )
                            }

                            is WrongCredentialsException -> _state.update {
                                it.copy(
                                    screenError = "Email and password doesn't match."
                                )
                            }

                            is CantConnectException -> _state.update {
                                it.copy(
                                    screenError = "Can't connect to the server. Please try again later."
                                )
                            }

                            else -> _state.update {
                                it.copy(
                                    screenError = resource.exception?.message
                                )
                            }
                        }
                    }

                    is Resource.Loading -> _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }

                    is Resource.Success -> _state.update {
                        it.copy(
                            isLoading = false,
                            navDestination = LoginDestination.HomeDestination
                        )
                    }
                }
            }
        }
    }

    fun resetEvents() {
        _state.update {
            it.copy(
                navDestination = null,
                screenError = null,
            )
        }
    }
}