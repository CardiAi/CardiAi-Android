package com.codlin.cardiai.presentation.new_record

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.codlin.cardiai.R
import com.codlin.cardiai.presentation.destinations.NewRecordScreenDestination
import com.codlin.cardiai.presentation.destinations.RecordResultScreenDestination
import com.codlin.cardiai.presentation.navigation.HomeNavGraph
import com.codlin.cardiai.presentation.new_record.components.MCQuestion
import com.codlin.cardiai.presentation.new_record.components.NumericalQuestionsPage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo

@HomeNavGraph
@Destination
@Composable
fun NewRecordScreen(
    navigator: DestinationsNavigator,
    patientId: Int,
) {
    val viewModel: NewRecordViewModel =
        hiltViewModel<NewRecordViewModel, NewRecordViewModel.NewRecordViewModelFactory> {
            it.create(patientId)
        }

    val state by viewModel.state.collectAsStateWithLifecycle()

    BackHandler {
        viewModel.onEvent(NewRecordEvent.OnBackClicked)
    }

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = state) {
        state.navDestination?.let {
            when (it) {
                is NewRecordDestination.RecordResultsDestination -> navigator.navigate(
                    RecordResultScreenDestination(it.record)
                ) {
                    popUpTo(NewRecordScreenDestination) {
                        inclusive = true
                    }
                }

                NewRecordDestination.NavigateUp -> navigator.popBackStack()
            }
        }
        state.screenError?.let {
            snackBarHostState.showSnackbar(it)
        }
        viewModel.resetEvents()
    }


    Scaffold(snackbarHost = { SnackbarHost(hostState = snackBarHostState) }) {
        NewRecordContent(
            state = state,
            onEvent = viewModel::onEvent,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun NewRecordContent(
    state: NewRecordState,
    onEvent: (NewRecordEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState {
        state.questions.size + 1
    }
    LaunchedEffect(key1 = state.currentPageIndex) {
        pagerState.animateScrollToPage(
            state.currentPageIndex,
            animationSpec = tween(delayMillis = 700)
        )
    }
    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        contentPadding = PaddingValues(24.dp),
        userScrollEnabled = false,
        pageSpacing = 24.dp,
    ) { page ->
        if (page < 5) {
            MCQuestion(
                question = state.questions[page],
                imageResId = getQuestionImage(page),
                onSelectChoice = {
                    onEvent(NewRecordEvent.OnMCQuestionAnswered(it))
                }
            )
        } else {
            NumericalQuestionsPage(
                questionsList = state.numericalQuestions,
                onQuestionValueChange = { index, value ->
                    onEvent(
                        NewRecordEvent.OnNumericalValueChange(
                            index = index,
                            value = value
                        )
                    )
                },
                onConfirmClicked = { onEvent(NewRecordEvent.OnConfirm) }
            )
        }
    }
}

private fun getQuestionImage(page: Int): Int = when (page) {
    0 -> R.drawable.q1
    1 -> R.drawable.q2
    2 -> R.drawable.q3
    3 -> R.drawable.q4
    4 -> R.drawable.q5
    else -> R.drawable.q1
}