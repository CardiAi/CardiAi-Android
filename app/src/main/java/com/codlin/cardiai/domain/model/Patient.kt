package com.codlin.cardiai.domain.model

data class Patient(
    val id: Int? = null,
    val name: String? = null,
    val gender: Gender? = null,
    val age: Int? = null,
    val lastRecordDate: String? = null,
    val lastResult: Int? = null,
)

enum class Gender {
    Male, Female
}