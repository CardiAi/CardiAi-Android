package com.codlin.cardiai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codlin.cardiai.domain.usecase.auth.CheckActiveUserUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkActiveUserUsecase: CheckActiveUserUsecase
) : ViewModel() {
    private val _hasActiveUser = MutableStateFlow(false)
    val hasActiveUser = _hasActiveUser.asStateFlow()

    var isAppReady: Boolean = false
        private set

    init {
        viewModelScope.launch {
            _hasActiveUser.update { checkActiveUserUsecase() }
            isAppReady = true
        }
    }
}