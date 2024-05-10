package com.codlin.cardiai.presentation.home.record_details

sealed class RecordDetailsEvent {
    data object OnBackClicked : RecordDetailsEvent()
}