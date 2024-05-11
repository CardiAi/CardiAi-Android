package com.codlin.cardiai.presentation.home.patient_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.codlin.cardiai.R
import com.codlin.cardiai.domain.model.Patient
import com.codlin.cardiai.domain.model.record.Record
import com.codlin.cardiai.presentation.UIFormatter
import com.codlin.cardiai.presentation.components.RecordIcon
import com.codlin.cardiai.presentation.destinations.NewRecordScreenDestination
import com.codlin.cardiai.presentation.destinations.RecordDetailsScreenDestination
import com.codlin.cardiai.presentation.home.components.BottomSheet
import com.codlin.cardiai.presentation.home.components.PaginationLazyColumn
import com.codlin.cardiai.presentation.home.patient_details.components.RecordItem
import com.codlin.cardiai.presentation.home.patients_list.components.StartDiagnosisButton
import com.codlin.cardiai.presentation.navigation.HomeNavGraph
import com.codlin.cardiai.presentation.theme.Neutral500
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator

@OptIn(ExperimentalMaterial3Api::class)
@HomeNavGraph
@Destination(navArgsDelegate = Patient::class)
@Composable
fun PatientDetailsScreen(
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<Boolean>,
    patient: Patient,
) {
    val viewModel: PatientDetailsViewModel =
        hiltViewModel<PatientDetailsViewModel, PatientDetailsViewModel.PatientDetailsViewModelFactory> {
            it.create(patient)
        }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val records = viewModel.records.collectAsLazyPagingItems()

    val snackbarHostState = remember { SnackbarHostState() }
    val bottomSheetState = rememberModalBottomSheetState()

    LaunchedEffect(key1 = state.isBottomSheetVisible) {
        if (!state.isBottomSheetVisible) {
            bottomSheetState.hide()
        } else {
            bottomSheetState.expand()
        }
    }
    LaunchedEffect(key1 = state.navDestination, key2 = state.screenError) {
        state.navDestination?.let {
            when (it) {
                is PatientDetailsDestination.RecordDetailsDestination -> navigator.navigate(
                    RecordDetailsScreenDestination(it.record)
                )

                is PatientDetailsDestination.NewRecordDestination -> navigator.navigate(
                    NewRecordScreenDestination(it.patientId)
                )

                is PatientDetailsDestination.NavigateUp -> resultNavigator.navigateBack(result = it.refresh)
            }
        }
        state.screenError?.let {
            snackbarHostState.showSnackbar(message = it)
        }
        viewModel.resetEvents()
    }


    PatientDetailsContent(
        state = state,
        records = records,
        onEvent = viewModel::onEvent,
        snackbarHostState = snackbarHostState,
        sheetState = bottomSheetState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PatientDetailsContent(
    state: PatientDetailsState,
    records: LazyPagingItems<Record>,
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
                        verticalAlignment = Alignment.CenterVertically,
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
                    .padding(vertical = 8.dp),
                color = Neutral500,
            )
            PaginationLazyColumn(
                pagingItems = records,
                loadingItem = {
                    RecordItem(
                        record = Record(),
                        onClick = { },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                },
                emptyListComposable = {
                    Box(
                        modifier = Modifier.fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "This patient has no records yet.",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
            ) { record ->
                RecordItem(
                    record = record,
                    onClick = {
                        onEvent(PatientDetailsEvent.OnRecordClicked(record))
                    },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                )
            }
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
                onEvent(PatientDetailsEvent.OnEditPatient(age = it))
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