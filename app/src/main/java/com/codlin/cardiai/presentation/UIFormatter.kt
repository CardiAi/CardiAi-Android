package com.codlin.cardiai.presentation

import com.codlin.cardiai.data.datasource.remote.dto.records.replaceUnderscoreWithSpace

object UIFormatter {
    fun formatRecordResult(degree: Int?): String = when (degree) {
        0 -> "Healthy"
        1 -> "Degree I"
        2 -> "Degree II"
        3 -> "Degree III"
        4 -> "Degree IV"
        else -> "No records yet."
    }

    fun <T> formatChoice(choice: T): String =
        choice.toString().replaceUnderscoreWithSpace().capitalizeWords()

    fun String.capitalizeWords(): String = split(" ").map { it.capitalize() }.joinToString(" ")
}