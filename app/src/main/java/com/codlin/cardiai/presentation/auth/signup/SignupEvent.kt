package com.codlin.cardiai.presentation.auth.signup

sealed class SignupEvent {
    data class OnNameChanged(val name: String) : SignupEvent()
    data class OnEmailChanged(val email: String) : SignupEvent()
    data class OnPasswordChanged(val password: String) : SignupEvent()
    data object TogglePasswordVisibility : SignupEvent()
    data object OnSignupClicked : SignupEvent()
    data object OnLoginClicked : SignupEvent()
}