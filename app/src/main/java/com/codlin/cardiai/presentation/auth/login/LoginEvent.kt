package com.codlin.cardiai.presentation.auth.login

sealed class LoginEvent {
    data class OnEmailChange(val value: String) : LoginEvent()
    data class OnPasswordChange(val value: String) : LoginEvent()
    data object TogglePasswordVisibility : LoginEvent()
    data object OnLoginClicked : LoginEvent()
    data object OnSignupClicked : LoginEvent()
}