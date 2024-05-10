package com.codlin.cardiai.presentation.home.patients_list

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.codlin.cardiai.domain.model.Patient
import com.codlin.cardiai.presentation.NavGraphs
import com.codlin.cardiai.presentation.components.SearchField
import com.codlin.cardiai.presentation.components.isScrollingUp
import com.codlin.cardiai.presentation.destinations.NewRecordScreenDestination
import com.codlin.cardiai.presentation.destinations.PatientDetailsScreenDestination
import com.codlin.cardiai.presentation.home.components.PaginationLazyColumn
import com.codlin.cardiai.presentation.home.patient_details.components.BottomSheet
import com.codlin.cardiai.presentation.home.patients_list.components.AddPatientButton
import com.codlin.cardiai.presentation.home.patients_list.components.ConfirmSelectionButton
import com.codlin.cardiai.presentation.home.patients_list.components.PatientItem
import com.codlin.cardiai.presentation.home.patients_list.components.StartDiagnosisButton
import com.codlin.cardiai.presentation.navigation.HomeNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient

@OptIn(ExperimentalMaterial3Api::class)
@HomeNavGraph(start = true)
@Destination
@Composable
fun PatientListScreen(
    viewModel: PatientListViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    patientDetailsResult: ResultRecipient<PatientDetailsScreenDestination, Boolean>,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val patients = viewModel.patients.collectAsLazyPagingItems()

    patientDetailsResult.onNavResult { result ->
        if (result is NavResult.Value) {
            if (result.value) {
                viewModel.refreshPatients()
            }
        }
    }

    BackHandler(enabled = state.isBottomSheetVisible || state.isSearchVisible || state.inSelectMode) {
        viewModel.onEvent(PatientListEvent.OnBackClicked)
    }

    LaunchedEffect(key1 = state.navDestination) {
        state.navDestination?.let { destination ->
            when (destination) {
                PatientsListDestination.AuthDestination -> {
                    navigator.navigate(NavGraphs.auth) {
                        popUpTo(NavGraphs.home) {
                            inclusive = true
                        }
                    }
                }

                is PatientsListDestination.PatientsDetailsDestination -> {
                    navigator.navigate(PatientDetailsScreenDestination(destination.patient))
                }

                is PatientsListDestination.NewRecordDestination -> navigator.navigate(
                    NewRecordScreenDestination(destination.patientId)
                )
            }
        }
        viewModel.resetEvents()
    }

    PatientListContent(state, patients, viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PatientListContent(
    state: PatientListState,
    patients: LazyPagingItems<Patient>,
    onEvent: (PatientListEvent) -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(),
) {
    val listState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            AnimatedContent(targetState = state.isSearchVisible, label = "SearchBarAnimation") {
                if (it)
                    SearchField(
                        query = state.searchQuery,
                        onQueryChange = { query ->
                            onEvent(
                                PatientListEvent.OnSearchQueryChange(
                                    query
                                )
                            )
                        },
                        onCancelClicked = { onEvent(PatientListEvent.ToggleSearchVisibility) },
                        onSearchClicked = { onEvent(PatientListEvent.InstantSearch) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        cancelable = !state.inSelectMode,
                    )
                else
                    TopAppBar(
                        title = {
                            Text(
                                text = "Patients List",
                                style = MaterialTheme.typography.headlineSmall
                            )
                        },
                        actions = {
                            IconButton(onClick = { onEvent(PatientListEvent.ToggleSearchVisibility) }) {
                                Icon(
                                    imageVector = Icons.Outlined.Search,
                                    contentDescription = "Search",
                                )
                            }
                            IconButton(onClick = { onEvent(PatientListEvent.ToggleMenu) }) {
                                Icon(
                                    imageVector = Icons.Filled.MoreVert,
                                    contentDescription = "Overflow Menu",
                                )
                            }
                            DropdownMenu(
                                expanded = state.isMenuShown,
                                onDismissRequest = { onEvent(PatientListEvent.ToggleMenu) },
                            ) {
                                DropdownMenuItem(
                                    onClick = { onEvent(PatientListEvent.OnLogout) },
                                    text = {
                                        Text(
                                            text = "Logout",
                                            modifier = Modifier.padding(start = 32.dp)
                                        )
                                    },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Outlined.Logout,
                                            contentDescription = null
                                        )
                                    }
                                )
                            }
                        },
                        scrollBehavior = scrollBehavior
                    )
            }
        },
        floatingActionButton = {
            AnimatedContent(targetState = state.inSelectMode, label = "FABChange") {
                if (it) {
                    AddPatientButton(
                        expanded = listState.isScrollingUp(),
                        onClick = { onEvent(PatientListEvent.OnAddPatientClicked) }
                    )
                } else {
                    StartDiagnosisButton(
                        expanded = listState.isScrollingUp(),
                        onClick = {
                            onEvent(PatientListEvent.OnStartDiagnosisClicked)
                        },
                    )
                }
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = state.selectedId != null,
                enter = slideInVertically(initialOffsetY = { it / 2 }),
                exit = slideOutVertically(targetOffsetY = { it / 2 }),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
            ) {
                state.selectedId?.let {
                    ConfirmSelectionButton(
                        onClick = { onEvent(PatientListEvent.OnContinueClicked) },
                    )
                }
            }
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        PaginationLazyColumn(
            pagingItems = patients,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            loadingItem = {
                PatientItem(
                    patient = Patient(),
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
                        text = "You have no patients added yet.",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            },
            lazyListState = listState,
        ) { patient ->
            PatientItem(
                patient = patient,
                onClick = {
                    onEvent(PatientListEvent.OnPatientClicked(patient))
                },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                selected = patient.id == state.selectedId,
            )
            BottomSheet(
                patient = state.addedPatient,
                isVisible = state.isBottomSheetVisible,
                sheetState = sheetState,
                onNameChanged = { onEvent(PatientListEvent.OnAddPatient(name = it)) },
                onAgeChanged = { onEvent(PatientListEvent.OnAddPatient(age = it)) },
                onGenderChanged = { onEvent(PatientListEvent.OnAddPatient(gender = it)) },
                onSubmitClicked = { onEvent(PatientListEvent.OnConfirmAdd) },
                onDismiss = { onEvent(PatientListEvent.OnBottomSheetDismissed) })
        }
    }
}

@Preview
@Composable
private fun PatientListPreview() {
//    PatientListContent(PatientListState(), PageD, {})
}