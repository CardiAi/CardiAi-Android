package com.codlin.cardiai.presentation.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.codlin.cardiai.presentation.theme.CardiAiTheme
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
    LaunchedEffect(key1 = state.value) {
        when (state.value.navDestination) {
            null -> return@LaunchedEffect
            LoginDestination.HomeDestination -> {
                navigator.navigate(NavGraphs.home) {
                    popUpTo("auth") {
                        inclusive = true
                    }
                }
            }

            LoginDestination.SignupDestination -> navigator.navigate(SignupScreenDestination)
        }
        viewModel.resetEvents()
    }
    LoginContent(state = state.value, onEvent = viewModel::onEvent)
}

@Composable
private fun LoginContent(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
) {
    Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
        Logo(
            Modifier
                .align(Alignment.TopStart),
        )
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
                        .fillMaxWidth()
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
                        .fillMaxWidth()
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
@Preview(showBackground = true)
private fun LoginPreview() {
    CardiAiTheme{
        LoginContent(
            state = LoginState(),
            onEvent = {},
        )
    }
}