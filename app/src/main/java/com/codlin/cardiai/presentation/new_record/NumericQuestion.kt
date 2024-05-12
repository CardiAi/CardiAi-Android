package com.codlin.cardiai.presentation.new_record

data class NumericQuestion(
    val title: String,
    val placeholder: String,
    val maxValue: Double,
    val minValue: Double,
    val isRequired: Boolean = false,
    val isDecimal: Boolean = false,
    val unit: String? = null,
    val answer: String? = null,
) {
    val isAnswerValid
        get() = try {
            answer?.toDouble()?.let { it in minValue..maxValue }
        } catch (_: Exception) {
            false
        }
}