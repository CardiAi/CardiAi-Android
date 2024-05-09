package com.codlin.cardiai.presentation.new_record

data class Question<T>(
    val question: String,
    val choices: List<T>,
    val answerIndex: Int? = null,
) {
    val answer
        get() = answerIndex?.let { choices[it] }
}