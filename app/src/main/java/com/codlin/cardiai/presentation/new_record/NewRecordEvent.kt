package com.codlin.cardiai.presentation.new_record

sealed class NewRecordEvent {
    data class OnMCQuestionAnswered(val answerIndex: Int) : NewRecordEvent()
    data class OnNumericalValueChange(val index: Int, val value: String) : NewRecordEvent()
    data object OnConfirm : NewRecordEvent()
    data object OnBackClicked : NewRecordEvent()
}