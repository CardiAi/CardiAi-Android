package com.codlin.cardiai.presentation.home.patients_list

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.codlin.cardiai.presentation.NavGraphs
import com.codlin.cardiai.presentation.components.SearchField
import com.codlin.cardiai.presentation.destinations.PatientDetailsScreenDestination
import com.codlin.cardiai.presentation.home.patients_list.components.PatientItem
import com.codlin.cardiai.presentation.home.patients_list.components.StartDiagnosisButton
import com.codlin.cardiai.presentation.navigation.HomeNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo

@HomeNavGraph(start = true)
@Destination
@Composable
fun PatientListScreen(
    viewModel: PatientListViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = state) {
        state.navDestination?.let { destination ->
            when (destination) {
                PatientDestination.AuthDestination -> {
                    navigator.navigate(NavGraphs.auth) {
                        popUpTo(NavGraphs.home) {
                            inclusive = true
                        }
                    }
                }

                is PatientDestination.PatientDetailsDestination -> {
                    navigator.navigate(PatientDetailsScreenDestination(destination.patient))
                }

                PatientDestination.StartDiagnosisDestination -> {
                    TODO()
                }
            }
        }
        viewModel.resetEvents()
    }

    PatientListContent(state, viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PatientListContent(
    state: PatientListState,
    onEvent: (PatientListEvent) -> Unit,
) {
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
                            .padding(horizontal = 16.dp)
                    )
                else
                    TopAppBar(
                        title = { Text(text = "Patients List") },
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
                        }
                    )
            }
        },
        floatingActionButton = {
            StartDiagnosisButton(onClick = {
                onEvent(PatientListEvent.OnStartDiagnosisClicked)
            })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            if (state.patients.isNullOrEmpty()) {
                if (state.isLoading) {
                    CircularProgressIndicator()
                } else {
                    Text(
                        text = "You have no patients added yet.",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(state.patients, key = { it.id }) {
                        PatientItem(
                            patient = it,
                            onClick = {
                                onEvent(PatientListEvent.OnPatientClicked(it))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PatientListPreview() {
    PatientListContent(PatientListState(), {})
}