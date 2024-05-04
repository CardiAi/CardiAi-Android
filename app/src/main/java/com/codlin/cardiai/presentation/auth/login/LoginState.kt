package com.codlin.cardiai.presentation.auth.login

data class LoginState(
    val emailValue: String = "",
    val passwordValue: String = "",
    val isPasswordVisible: Boolean = false,
    val navDestination: LoginDestination? = null,
)

sealed interface LoginDestination {
    data object HomeDestination : LoginDestination
    data object SignupDestination : LoginDestination
}