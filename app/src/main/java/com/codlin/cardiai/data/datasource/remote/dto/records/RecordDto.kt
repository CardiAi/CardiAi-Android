package com.codlin.cardiai.data.datasource.remote.dto.records

data class RecordDto(
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