package com.codlin.cardiai.data.datasource.remote.dto.patients

data class PatientDto(
    val id: Int,
    val name: String,
    val gender: String,
    val age: String,
    val lastRecordDate: Long?,
    val lastResult: Int?,
    val createdAt: Long,
)