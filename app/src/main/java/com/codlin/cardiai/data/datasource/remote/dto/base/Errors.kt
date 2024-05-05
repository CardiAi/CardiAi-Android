package com.codlin.cardiai.data.datasource.remote.dto.base

data class Errors(
    val email: List<String>?,
    val password: List<String>?,
    val name: List<String>?,
    val gender: List<String>?,
    val age: List<String>?,
)