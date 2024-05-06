package com.codlin.cardiai.presentation.auth.signup

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
import androidx.compose.runtime.getValue
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
import com.codlin.cardiai.presentation.destinations.LoginScreenDestination
import com.codlin.cardiai.presentation.navigation.AuthNavGraph
import com.codlin.cardiai.presentation.theme.CardiAiTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo

@AuthNavGraph
@Destination
@Composable
fun SignupScreen(
    viewModel: SignupViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = state) {
        state.navDestination?.let {
            navigator.navigate(LoginScreenDestination) {
                popUpTo(NavGraphs.auth.startRoute) {
                    inclusive = false
                }
            }
        }
        state.screenError?.let { error ->
            snackbarHostState.showSnackbar(error)
        }
        state.nameError?.let { error ->
            snackbarHostState.showSnackbar(error)
        }
        state.emailError?.let { error ->
            snackbarHostState.showSnackbar(error)
        }
        state.passwordError?.let { error ->
            snackbarHostState.showSnackbar(error)
        }
        viewModel.resentEvent()
    }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        SignupContent(
            state = state,
            onEvent = viewModel::onEvent,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
private fun SignupContent(
    state: SignupState,
    onEvent: (SignupEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
        Logo(
            Modifier.align(Alignment.TopStart)
        )
        AnimatedVisibility(visible = state.isLoading, modifier = Modifier.align(Alignment.Center)) {
            CircularProgressIndicator()
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                Text(
                    text = "Sign up",
                    style = MaterialTheme.typography.displaySmall,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Create account on CardiAi",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(64.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ThemeTextField(
                    value = state.nameValue,
                    onValueChange = { onEvent(SignupEvent.OnNameChanged(it)) },
                    label = "Name",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    isError = !state.nameError.isNullOrBlank()
                )

                ThemeTextField(
                    value = state.emailValue,
                    onValueChange = { onEvent(SignupEvent.OnEmailChanged(it)) },
                    label = "Email",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    isError = !state.emailError.isNullOrBlank()
                )

                ThemePasswordField(
                    value = state.passwordValue,
                    onValueChange = { onEvent(SignupEvent.OnPasswordChanged(it)) },
                    onVisibilityIconClicked = { onEvent(SignupEvent.TogglePasswordVisibility) },
                    isPasswordVisible = state.isPasswordVisible,
                    modifier = Modifier.fillMaxWidth(),
                    isError = !state.passwordError.isNullOrBlank()
                )

            }

            Spacer(modifier = Modifier.height(100.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                ThemeButton(
                    text = "Sign up",
                    onClick = { onEvent(SignupEvent.OnSignupClicked) })

                HorizontalOrLine()

                ThemeButton(
                    text = "Login",
                    onClick = { onEvent(SignupEvent.OnLoginClicked) },
                    isPrimary = false
                )
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignupScreenPreview() {
    CardiAiTheme {
        SignupContent(
            state = SignupState(),
            onEvent = {})
    }
}