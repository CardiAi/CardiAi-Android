package com.codlin.cardiai.presentation.auth.signup

import androidx.lifecycle.ViewModel
import com.codlin.cardiai.domain.usecase.SignupUsecase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SignupViewModel @Inject constructor(private val signupUsecase: SignupUsecase) : ViewModel() {
    private val _state = MutableStateFlow(SignupState())
    val state = _state.asStateFlow()

    fun onEvent(event: SignupEvent) {
        when (event) {
            is SignupEvent.OnNameChanged -> {
                _state.update {
                    it.copy(
                        nameValue = event.name
                    )
                }
            }

            is SignupEvent.OnEmailChanged -> {
                _state.update {
                    it.copy(
                        emailValue = event.email
                    )
                }
            }

            is SignupEvent.OnPasswordChanged -> {
                _state.update {
                    it.copy(
                        passwordValue = event.password
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
                _state.update {
                    it.copy(
                        navDestination = SignupDestination.LoginDestination
                    )
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
                navDestination = null
            )
        }
    }
}