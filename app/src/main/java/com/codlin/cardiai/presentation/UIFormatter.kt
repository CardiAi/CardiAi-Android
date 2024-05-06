package com.codlin.cardiai.presentation

object UIFormatter {
    fun formatRecordResult(degree: Int?): String = when (degree) {
        0 -> "Healthy"
        1 -> "Degree I"
        2 -> "Degree II"
        3 -> "Degree III"
        4 -> "Degree IV"
        else -> "No records yet."
    }
}