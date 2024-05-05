package com.codlin.cardiai.presentation.home.patientsList

import android.media.Image

data class PatientListState(
    val nameValue: String = "",
    val degreeValue: String = "",
    val dateValue: String = "",
    val image: Image,
    val navDestination: PatientDestination? = null
)

sealed interface PatientDestination {
    data object PatientDetails : PatientDestination
}
