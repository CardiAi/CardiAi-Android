package com.codlin.cardiai.presentation.new_record

data class NumericQuestion(
    val title: String,
    val placeholder: String,
    val unit: String? = null,
    var answer: String? = null,
)