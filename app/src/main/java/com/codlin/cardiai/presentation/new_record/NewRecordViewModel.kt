package com.codlin.cardiai.presentation.new_record

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class NewRecordViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(NewRecordState())
    val state = _state.asStateFlow()

    fun onEvent(event: NewRecordEvent) {
        when (event) {
            is NewRecordEvent.OnMCQuestionAnswered -> {
                _state.update {
                    it.copy(
                        questions = it.questions.mapIndexed { index, question ->
                            if (index == _state.value.currentPageIndex) question.copy(
                                answerIndex = event.answerIndex
                            ) else question
                        },
                        currentPageIndex = it.currentPageIndex + 1
                    )
                }
            }

            is NewRecordEvent.OnNumericalValueChange -> {
                _state.update {
                    it.copy(
                        numericalQuestions = it.numericalQuestions.mapIndexed { index, question ->
                            if (event.index == index) {
                                question.copy(
                                    answer = event.value
                                )
                            } else {
                                question
                            }
                        }
                    )
                }
            }

            NewRecordEvent.OnConfirm -> {

            }
        }
    }
}