package com.codlin.cardiai.presentation.auth.signup

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
import com.codlin.cardiai.presentation.auth.components.HorizontalOrLine
import com.codlin.cardiai.presentation.auth.components.Logo
import com.codlin.cardiai.presentation.components.ThemeButton
import com.codlin.cardiai.presentation.components.ThemePasswordField
import com.codlin.cardiai.presentation.components.ThemeTextField
import com.codlin.cardiai.presentation.destinations.LoginScreenDestination
import com.codlin.cardiai.presentation.navigation.AuthNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@AuthNavGraph
@Destination
@Composable
fun SignupScreen(
    viewModel: SignupViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = state.value) {
        when (state.value.navDestination) {
            null -> return@LaunchedEffect
            SignupDestination.LoginDestination -> navigator.navigate(LoginScreenDestination)
        }
        viewModel.resentEvent()
    }
    SignupContent(state = state.value, onEvent = viewModel::onEvent)
}

@Composable
private fun SignupContent(
    state: SignupState,
    onEvent: (SignupEvent) -> Unit
) {
    Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
        Logo(
            Modifier.align(Alignment.TopStart)
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
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
                )

                ThemeTextField(
                    value = state.emailValue,
                    onValueChange = { onEvent(SignupEvent.OnEmailChanged(it)) },
                    label = "Email",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )

                ThemePasswordField(
                    value = state.passwordValue,
                    onValueChange = { onEvent(SignupEvent.OnPasswordChanged(it)) },
                    onVisibilityIconClicked = { onEvent(SignupEvent.TogglePasswordVisibility) },
                    isPasswordVisible = state.isPasswordVisible,
                    modifier = Modifier.fillMaxWidth(),
                )

            }

            Spacer(modifier = Modifier.height(50.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                ThemeButton(
                    text = "Sign up" ,
                    onClick = { onEvent(SignupEvent.OnSignupClicked) })

                HorizontalOrLine()

                ThemeButton(
                    text = "Login",
                    onClick = { onEvent(SignupEvent.OnLoginClicked) },
                    isPrimary = false)
            }
        }
    }
}

@Preview
@Composable
private fun SignupScreenPreview() {
    SignupContent(
        state = SignupState(),
        onEvent = {})
}