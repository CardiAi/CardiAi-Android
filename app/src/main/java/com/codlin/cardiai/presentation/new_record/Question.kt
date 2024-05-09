package com.codlin.cardiai.presentation.new_record

data class Question<T>(
    val question: String,
    val choices: List<T>,
    var answerIndex: Int? = null,
)