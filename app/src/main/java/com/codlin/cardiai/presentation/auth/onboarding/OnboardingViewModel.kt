package com.codlin.cardiai.presentation.auth.onboarding

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(OnboardingState())
    val state = _state.asStateFlow()

    fun onEvent(event: OnboardingEvent) {
        when (event) {
            OnboardingEvent.NavigateToLogin -> _state.update {
                it.copy(
                    navDestination = OnboardingDestination.LoginDestination
                )
            }

            OnboardingEvent.NavigateToSignup -> _state.update {
                it.copy(
                    navDestination = OnboardingDestination.SignupDestination
                )
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