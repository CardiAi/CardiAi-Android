package com.codlin.cardiai.presentation.record_result

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.codlin.cardiai.domain.model.record.Record
import com.codlin.cardiai.presentation.UIFormatter
import com.codlin.cardiai.presentation.components.RecordIcon
import com.codlin.cardiai.presentation.components.ThemeButton
import com.codlin.cardiai.presentation.destinations.NewRecordScreenDestination
import com.codlin.cardiai.presentation.destinations.PatientListScreenDestination
import com.codlin.cardiai.presentation.navigation.HomeNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import timber.log.Timber

@HomeNavGraph
@Destination(navArgsDelegate = Record::class)
@Composable
fun RecordResultScreen(
    navigator: DestinationsNavigator,
    record: Record
) {
    val viewModel =
        hiltViewModel<RecordResultViewModel, RecordResultViewModel.RecordResultViewModelFactory> {
            it.create(record)
        }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    BackHandler {
        viewModel.onEvent(RecordResultEvent.OnBackClicked)
    }

    LaunchedEffect(key1 = state.navDestination, key2 = state.screenError) {
        state.navDestination?.let {
            when (it) {
                RecordResultDestination.PatientListDestination -> {
                    Timber.d("Navigate back to patientList")
                    navigator.navigate(PatientListScreenDestination) {
                        popUpTo(NewRecordScreenDestination) {
                            inclusive = true
                        }
                    }
                }
            }
        }
        state.screenError?.let {
            snackBarHostState.showSnackbar(it)
        }
        viewModel.resetEvents()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { padding ->
        AnimatedContent(
            targetState = state.isLoading,
            label = "Loading Animation",
            modifier = Modifier.padding(padding)
        ) {
            if (it) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Text(text = "Talking to the roboty thing to get the result.")
                }
            } else {
                RecordResultContent(state = state, onEvent = viewModel::onEvent)
            }
        }
    }
}

@Composable
private fun RecordResultContent(
    state: RecordResultState,
    onEvent: (RecordResultEvent) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = state.record.patientName!!,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 24.dp)
        )
        RecordIcon(
            result = state.record.result,
            modifierBackground = Modifier.size(160.dp),
            modifierHeart = Modifier.size(92.dp)
        )
        Text(
            text = UIFormatter.formatRecordResult(state.record.result),
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 24.dp),
        )
        Spacer(modifier = Modifier.height(170.dp))
        ThemeButton(
            text = "Continue",
            onClick = { onEvent(RecordResultEvent.OnContinueClicked) }
        )
    }
}