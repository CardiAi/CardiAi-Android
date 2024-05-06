package com.codlin.cardiai.presentation.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codlin.cardiai.domain.usecase.auth.SignupUsecase
import com.codlin.cardiai.domain.util.Resource
import com.codlin.cardiai.domain.util.exception.BlankPasswordException
import com.codlin.cardiai.domain.util.exception.CantConnectException
import com.codlin.cardiai.domain.util.exception.EmailTakenException
import com.codlin.cardiai.domain.util.exception.InvalidEmailException
import com.codlin.cardiai.domain.util.exception.ShortNameException
import com.codlin.cardiai.domain.util.exception.ShortPasswordException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val signupUsecase: SignupUsecase) : ViewModel() {
    private val _state = MutableStateFlow(SignupState())
    val state = _state.asStateFlow()

    fun onEvent(event: SignupEvent) {
        when (event) {
            is SignupEvent.OnNameChanged -> {
                _state.update {
                    it.copy(
                        nameValue = event.name,
                        nameError = null,
                    )
                }
            }

            is SignupEvent.OnEmailChanged -> {
                _state.update {
                    it.copy(
                        emailValue = event.email,
                        emailError = null,
                    )
                }
            }

            is SignupEvent.OnPasswordChanged -> {
                _state.update {
                    it.copy(
                        passwordValue = event.password,
                        passwordError = null,
                    )
                }
            }

            SignupEvent.TogglePasswordVisibility -> {
                _state.update {
                    it.copy(
                        isPasswordVisible = !it.isPasswordVisible
                    )
                }
            }

            SignupEvent.OnSignupClicked -> {
                viewModelScope.launch {
                    signupUsecase(
                        _state.value.nameValue.trim(),
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

                                    is EmailTakenException -> _state.update {
                                        it.copy(
                                            emailError = "This email address is already used."
                                        )
                                    }

                                    is CantConnectException -> _state.update {
                                        it.copy(
                                            screenError = "Can't connect to the server. Please try again later."
                                        )
                                    }

                                    is ShortNameException -> _state.update {
                                        it.copy(
                                            nameError = "Name has to be at least 4 characters."
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

                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        navDestination = SignupDestination.LoginDestination
                                    )
                                }
                            }
                        }
                    }
                }
            }

            SignupEvent.OnLoginClicked -> {
                _state.update {
                    it.copy(
                        navDestination = SignupDestination.LoginDestination
                    )
                }
            }
        }
    }

    fun resentEvent() {
        _state.update {
            it.copy(
                navDestination = null,
                screenError = null,
            )
        }
    }
}