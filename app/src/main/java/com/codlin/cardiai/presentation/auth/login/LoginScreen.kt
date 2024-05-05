package com.codlin.cardiai.presentation.auth.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.codlin.cardiai.presentation.NavGraphs
import com.codlin.cardiai.presentation.auth.components.HorizontalOrLine
import com.codlin.cardiai.presentation.auth.components.Logo
import com.codlin.cardiai.presentation.components.ThemeButton
import com.codlin.cardiai.presentation.components.ThemePasswordField
import com.codlin.cardiai.presentation.components.ThemeTextField
import com.codlin.cardiai.presentation.destinations.SignupScreenDestination
import com.codlin.cardiai.presentation.navigation.AuthNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@AuthNavGraph
@Destination
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = state.value) {
        state.value.navDestination?.let {
            when (it) {
                LoginDestination.HomeDestination -> {
                    navigator.navigate(NavGraphs.home) {
                        popUpTo("auth") {
                            inclusive = true
                        }
                    }
                }

                LoginDestination.SignupDestination -> navigator.navigate(SignupScreenDestination)
            }
        }
        state.value.screenError?.let { error ->
            snackbarHostState.showSnackbar(error)
        }
        state.value.emailError?.let { error ->
            snackbarHostState.showSnackbar(error)
        }
        state.value.passwordError?.let { error ->
            snackbarHostState.showSnackbar(error)
        }
        viewModel.resetEvents()
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        LoginContent(
            state = state.value,
            onEvent = viewModel::onEvent,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
private fun LoginContent(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
        Logo(
            Modifier
                .align(Alignment.TopStart),
        )
        AnimatedVisibility(visible = state.isLoading, modifier = Modifier.align(Alignment.Center)) {
            CircularProgressIndicator()
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.displaySmall,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Welcome back to CardiAi",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                )
            }
            Spacer(modifier = Modifier.height(64.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ThemeTextField(
                    value = state.emailValue,
                    onValueChange = { onEvent(LoginEvent.OnEmailChange(it)) },
                    label = "Email",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                    isError = state.emailError != null,
                )
                ThemePasswordField(
                    value = state.passwordValue,
                    onValueChange = {
                        onEvent(LoginEvent.OnPasswordChange(it))
                    },
                    onVisibilityIconClicked = {
                        onEvent(LoginEvent.TogglePasswordVisibility)
                    },
                    isPasswordVisible = state.isPasswordVisible,
                    modifier = Modifier
                        .fillMaxWidth(),
                    isError = state.passwordError != null,
                )
            }
            Spacer(modifier = Modifier.height(140.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                ThemeButton(
                    text = "Login",
                    onClick = { onEvent(LoginEvent.OnLoginClicked) }
                )
                HorizontalOrLine()
                ThemeButton(
                    text = "Sign up",
                    onClick = { onEvent(LoginEvent.OnSignupClicked) },
                    isPrimary = false,
                )
            }
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
@Preview
private fun LoginPreview() {
    LoginContent(
        state = LoginState(),
        onEvent = {},
    )
}