package com.codlin.cardiai.presentation.auth.onboarding

data class OnboardingState(
    val navDestination: OnboardingDestination? = null
)

sealed interface OnboardingDestination {
    data object LoginDestination : OnboardingDestination
    data object SignupDestination : OnboardingDestination
}