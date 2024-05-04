package com.codlin.cardiai.presentation.auth.onboarding

sealed class OnboardingEvent {
    data object NavigateToLogin : OnboardingEvent()
    data object NavigateToSignup : OnboardingEvent()
}