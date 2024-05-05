package com.codlin.cardiai.presentation.auth.signup

data class SignupState(
    val nameValue: String = "",
    val emailValue: String = "",
    val passwordValue: String = "",
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val screenError: String? = null,
    val isLoading: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val navDestination: SignupDestination? = null,
)

sealed interface SignupDestination {
    data object LoginDestination : SignupDestination
}
