package com.codlin.cardiai.presentation.home.patients_list

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.codlin.cardiai.domain.model.Patient
import com.codlin.cardiai.domain.util.exception.NoDataException
import com.codlin.cardiai.presentation.NavGraphs
import com.codlin.cardiai.presentation.components.LazyPagingItemsError
import com.codlin.cardiai.presentation.components.SearchField
import com.codlin.cardiai.presentation.components.isScrollingUp
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
    val patients = viewModel.patients.collectAsLazyPagingItems()

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

    PatientListContent(state, patients, viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PatientListContent(
    state: PatientListState,
    patients: LazyPagingItems<Patient>,
    onEvent: (PatientListEvent) -> Unit,
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
                        },
                        scrollBehavior = scrollBehavior
                    )
            }
        },
        floatingActionButton = {
            StartDiagnosisButton(
                expanded = listState.isScrollingUp(),
                onClick = {
                    onEvent(PatientListEvent.OnStartDiagnosisClicked)
                },
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                state = listState,
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(patients.itemCount) { index ->
                    PatientItem(
                        patient = patients[index]!!,
                        onClick = {
                            onEvent(PatientListEvent.OnPatientClicked(patients[index]!!))
                        },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
                patients.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                Box(
                                    modifier = Modifier.fillParentMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val error = patients.loadState.refresh as LoadState.Error
                            if (error.error is NoDataException) {
                                if (itemCount == 0) {
                                    item {
                                        Box(
                                            modifier = Modifier.fillParentMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "You have no patients added yet.",
                                                style = MaterialTheme.typography.bodyLarge,
                                            )
                                        }
                                    }
                                }
                            } else {
                                item {
                                    LazyPagingItemsError(
                                        errorMessage = error.error.localizedMessage
                                            ?: "An unknown error has occurred."
                                    )
                                }
                            }
                        }

                        loadState.append is LoadState.Loading -> {
                            item {
                                PatientItem(
                                    patient = Patient(),
                                    onClick = { },
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                                )
                            }
                        }

                        /*loadState.refresh is LoadState.NotLoading -> {
                            if (!loadState.source.prepend.endOfPaginationReached) {
                                item {
                                    LazyPagingItemsError(errorMessage = "Unable to connect to the server.")
                                }
                            }
                        }*/

                        loadState.append is LoadState.Error -> {
                            item {
                                LazyPagingItemsError(
                                    errorMessage = "Unable to load more data."
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PatientListPreview() {
//    PatientListContent(PatientListState(), PageD, {})
}