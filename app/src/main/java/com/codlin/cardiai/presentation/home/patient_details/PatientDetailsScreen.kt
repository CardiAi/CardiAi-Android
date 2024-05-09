package com.codlin.cardiai.presentation.home.patient_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.codlin.cardiai.R
import com.codlin.cardiai.domain.model.Patient
import com.codlin.cardiai.presentation.UIFormatter
import com.codlin.cardiai.presentation.components.RecordIcon
import com.codlin.cardiai.presentation.home.patient_details.components.BottomSheet
import com.codlin.cardiai.presentation.home.patients_list.components.StartDiagnosisButton
import com.codlin.cardiai.presentation.navigation.HomeNavGraph
import com.codlin.cardiai.presentation.theme.CardiAiTheme
import com.codlin.cardiai.presentation.theme.Neutral500
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@HomeNavGraph
@Destination(navArgsDelegate = Patient::class)
@Composable
fun PatientDetailsScreen(
    navigator: DestinationsNavigator,
    patient: Patient,
) {
    val viewModel: PatientDetailsViewModel =
        hiltViewModel<PatientDetailsViewModel, PatientDetailsViewModel.PatientDetailsViewModelFactory> {
            it.create(patient)
        }

    val state by viewModel.state.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val bottomSheetState = rememberModalBottomSheetState()

    LaunchedEffect(key1 = state.isBottomSheetVisible) {
        if (!state.isBottomSheetVisible) {
            bottomSheetState.hide()
        } else {
            bottomSheetState.expand()
        }
    }
    LaunchedEffect(key1 = state) {
        state.navDestination?.let {
            when (it) {
                is PatientDetailsDestination.DiagnosisDetailsDestination -> TODO()
                is PatientDetailsDestination.StartDiagnosisDestination -> TODO()
                PatientDetailsDestination.NavigateUp -> navigator.popBackStack()
            }
        }
        state.screenError?.let {
            snackbarHostState.showSnackbar(message = it)
        }
        viewModel.resetEvents()
    }


    PatientDetailsContent(state, viewModel::onEvent, snackbarHostState, bottomSheetState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PatientDetailsContent(
    state: PatientDetailsState,
    onEvent: (PatientDetailsEvent) -> Unit,
    snackbarHostState: SnackbarHostState,
    sheetState: SheetState = rememberModalBottomSheetState(),
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { onEvent(PatientDetailsEvent.OnBackClicked) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = "Arrow Back",
                        )
                    }
                },
                actions = {

                    IconButton(onClick = { onEvent(PatientDetailsEvent.OnEditClicked) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.edit_icon),
                            contentDescription = "Edit",
                        )
                    }
                    IconButton(onClick = { onEvent(PatientDetailsEvent.OnDeleteClicked) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.delete_icon),
                            contentDescription = "Delete",
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            StartDiagnosisButton({ onEvent(PatientDetailsEvent.OnStartDiagnosisClicked) })
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                RecordIcon(
                    result = state.patient.lastResult,
                    modifierBackground = Modifier.size(98.dp),
                    modifierHeart = Modifier.size(49.dp)
                )
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = state.patient.name!!,
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.Black,
                    )
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = "${state.patient.age} years old",
                            style = MaterialTheme.typography.displayMedium,
                            color = Color.Black,
                        )
                        Spacer(modifier = Modifier.width(24.dp))
                        Text(
                            text = state.patient.gender.toString(),
                            style = MaterialTheme.typography.displayMedium,
                            color = Color.Black,
                        )
                    }
                    Text(
                        text = UIFormatter.formatRecordResult(state.patient.lastResult),
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp),
                color = Neutral500,
            )
            LazyColumn {

            }
            BottomSheet(
                patient = state.editablePatient,
                isVisible = state.isBottomSheetVisible,
                sheetState = sheetState,
                onNameChanged = {
                    onEvent(
                        PatientDetailsEvent.OnEditPatient(name = it)
                    )
                },
                onAgeChanged = {
                    onEvent(PatientDetailsEvent.OnEditPatient(age = it.toInt()))
                },
                onGenderChanged = {
                    onEvent(PatientDetailsEvent.OnEditPatient(gender = it))
                },
                onSubmitClicked = {
                    onEvent(PatientDetailsEvent.OnConfirmEdit)
                },
                onDismiss = {
                    onEvent(PatientDetailsEvent.OnBottomSheetDismissed)
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
private fun PatientDetailsPreview() {
    CardiAiTheme {
        PatientDetailsContent(
            state = PatientDetailsState(
                Patient(
                    name = "Ahmed",
                    age = 22,
                    lastResult = 2,
                )
            ),
            onEvent = {},
            SnackbarHostState(),
            rememberModalBottomSheetState()
        )
    }
}