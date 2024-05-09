package com.codlin.cardiai.presentation.new_record

import androidx.lifecycle.ViewModel
import com.codlin.cardiai.domain.model.record.Record
import com.codlin.cardiai.domain.model.record.RecordType
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
                _state.value.numericalQuestions.forEach { question ->
                    val error = if (question.answer.isNullOrBlank() && question.isRequired) {
                        "${question.placeholder} is required."
                    } else if (question.isAnswerValid == false) {
                        "${question.placeholder} has to be between ${question.minValue} and ${question.maxValue}"
                    } else {
                        null
                    }
                    error?.let {
                        _state.update {
                            it.copy(
                                screenError = error,
                            )
                        }
                        return
                    }
                }
                val record = _state.value.let {
                    Record(
                        id = null,
                        chestPain = it.questions[0].answer as RecordType.ChestPain,
                        ecg = it.questions[1].answer as RecordType.ECG,
                        slope = it.questions[2].answer as RecordType.Slope,
                        thal = it.questions[3].answer as RecordType.Thal,
                        exerciseAngina = (it.questions[4].answer as String) == "Yes",
                        bloodPressure = it.numericalQuestions[0].answer?.toInt(),
                        cholesterol = it.numericalQuestions[1].answer?.toInt(),
                        maxThal = it.numericalQuestions[2].answer!!.toInt(),
                        coronaryArtery = it.numericalQuestions[3].answer!!.toInt(),
                        bloodSugar = it.numericalQuestions[4].answer!!.toInt(),
                        oldPeak = it.numericalQuestions[5].answer!!.toDouble(),
                        result = null,
                        createdAt = null,
                    )
                }

                _state.update {
                    it.copy(
                        navDestination = NewRecordDestination.RecordResultsDestination(record)
                    )
                }
            }

            NewRecordEvent.OnBackClicked -> {
                if (_state.value.currentPageIndex == 0) {
                    _state.update {
                        it.copy(
                            navDestination = NewRecordDestination.NavigateUp
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            currentPageIndex = it.currentPageIndex - 1
                        )
                    }
                }
            }
        }
    }

    fun resetEvents() {
        _state.update {
            it.copy(
                navDestination = null,
                screenError = null,
            )
        }
    }
}