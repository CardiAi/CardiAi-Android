package com.codlin.cardiai.domain.model

data class Record(
    val id: Int,
    val bloodPressure: String,
    val bloodSugar: String,
    val chestPain: String,
    val cholesterol: String,
    val coronaryArtery: String,
    val createdAt: Long,
    val exerciseAngina: String,
    val maxThal: String,
    val oldPeak: String,
    val slope: String,
    val thal: String,
    val result: Int
)