package com.codlin.cardiai.domain.model

data class Patient(
    val id: Int = -1,
    val name: String = "",
    val age: Int = -1,
    val gender: Gender = Gender.MALE,
    val lastRecordDate: String? = null,
    val lastResult: Int? = null,
)

enum class Gender {
    MALE, FEMALE
}