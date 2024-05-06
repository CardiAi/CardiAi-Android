package com.codlin.cardiai.presentation.auth.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.codlin.cardiai.R
import com.codlin.cardiai.presentation.components.ThemeButton
import com.codlin.cardiai.presentation.destinations.LoginScreenDestination
import com.codlin.cardiai.presentation.destinations.SignupScreenDestination
import com.codlin.cardiai.presentation.navigation.AuthNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@AuthNavGraph(start = true)
@Destination
@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = state) {
        when (state.navDestination) {
            null -> return@LaunchedEffect
            OnboardingDestination.LoginDestination -> navigator.navigate(LoginScreenDestination)
            OnboardingDestination.SignupDestination -> navigator.navigate(SignupScreenDestination)
        }
        viewModel.resetEvents()
    }
    OnboardingContent(viewModel::onEvent)
}

@Composable
private fun OnboardingContent(onEvent: (OnboardingEvent) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = painterResource(id = R.drawable.onboarding_icon),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp)
                .aspectRatio(1f),
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "CardiAi",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp),
                textAlign = TextAlign.Center,
            )
            Text(
                text = "Your Heart diagnosis AI assistant.",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp),
                textAlign = TextAlign.Center,
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            ThemeButton(
                text = "Login",
                onClick = {
                    onEvent(OnboardingEvent.NavigateToLogin)
                },
            )
            ThemeButton(
                text = "Sign up",
                onClick = {
                    onEvent(OnboardingEvent.NavigateToSignup)
                },
                isPrimary = false,
            )
        }
    }
}

@Preview
@Composable
private fun OnboardingPreview() {

}