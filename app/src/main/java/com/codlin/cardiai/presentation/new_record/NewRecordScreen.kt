package com.codlin.cardiai.presentation.new_record

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.codlin.cardiai.R
import com.codlin.cardiai.presentation.navigation.HomeNavGraph
import com.codlin.cardiai.presentation.new_record.components.MCQuestion
import com.codlin.cardiai.presentation.new_record.components.NumericalQuestionsPage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@HomeNavGraph
@Destination
@Composable
fun NewRecordScreen(
    viewModel: NewRecordViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    patientId: Int,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    NewRecordContent(state = state, onEvent = viewModel::onEvent)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun NewRecordContent(
    state: NewRecordState,
    onEvent: (NewRecordEvent) -> Unit
) {
    val pagerState = rememberPagerState {
        state.questions.size + 1
    }
    LaunchedEffect(key1 = state.currentPageIndex) {
        pagerState.animateScrollToPage(state.currentPageIndex)
    }
    HorizontalPager(state = pagerState) { page ->
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