package com.codlin.cardiai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codlin.cardiai.domain.model.User
import com.codlin.cardiai.domain.usecase.auth.GetActiveUserUsecase
import com.codlin.cardiai.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getActiveUserUsecase: GetActiveUserUsecase
) : ViewModel() {
    private val _activeUser = MutableStateFlow<User?>(null)
    val activeUser = _activeUser.asStateFlow()

    var isAppReady: Boolean = false
        private set

    init {
        viewModelScope.launch {
            getActiveUserUsecase().collectLatest { resource ->
                if (resource is Resource.Success) {
                    _activeUser.update {
                        resource.data
                    }
                }
                isAppReady = true
            }
        }
    }
}