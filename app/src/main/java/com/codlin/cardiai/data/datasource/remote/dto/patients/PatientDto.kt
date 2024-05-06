package com.codlin.cardiai.data.datasource.remote.dto.patients

import com.codlin.cardiai.domain.model.Gender
import com.codlin.cardiai.domain.model.Patient

data class PatientDto(
    val id: Int,
    val name: String,
    val gender: String,
    val age: String,
    val lastRecordDate: String?,
    val lastResult: Int?,
    val createdAt: String,
) {
    fun toDomainModel(): Patient =
        Patient(
            id = id,
            name = name,
            age = age.toInt(),
            gender = if (gender.lowercase() == "male") Gender.Male else Gender.Female,
            lastRecordDate = lastRecordDate,
            lastResult = lastResult
        )
}