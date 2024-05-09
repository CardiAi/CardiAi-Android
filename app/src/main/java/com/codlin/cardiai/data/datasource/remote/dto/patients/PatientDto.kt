package com.codlin.cardiai.data.datasource.remote.dto.patients

import com.codlin.cardiai.domain.model.Gender
import com.codlin.cardiai.domain.model.Patient
import com.google.gson.annotations.SerializedName

data class PatientDto(
    val id: Int?,
    val name: String?,
    val gender: String?,
    val age: String?,
    @SerializedName("last_record_date")
    val lastRecordDate: String?,
    @SerializedName("last_result")
    val lastResult: Int?,
    @SerializedName("created_at")
    val createdAt: String?,
) {
    fun toDomainModel(): Patient =
        Patient(
            id = id,
            name = name,
            age = age?.toInt(),
            gender = if (gender?.lowercase() == "male") Gender.Male else Gender.Female,
            lastRecordDate = lastRecordDate,
            lastResult = lastResult
        )

    companion object {
        fun fromDomainModel(patient: Patient): PatientDto =
            patient.run {
                PatientDto(
                    id = id,
                    name = name,
                    gender = gender?.name?.lowercase(),
                    age = age.toString(),
                    lastRecordDate = lastRecordDate,
                    lastResult = lastResult,
                    createdAt = null
                )
            }
    }
}