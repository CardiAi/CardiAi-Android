package com.codlin.cardiai.data.datasource.remote.dto.records

import com.google.gson.annotations.SerializedName

data class RecordDto(
    val id: Int,
    @SerializedName("chest_pain")
    val chestPain: String,
    @SerializedName("blood_pressure")
    val bloodPressure: String,
    val cholesterol: String,
    @SerializedName("blood_sugar")
    val bloodSugar: String,
    @SerializedName("max_thal")
    val maxThal: String,
    @SerializedName("exercise_angina")
    val exerciseAngina: String,
    @SerializedName("old_peak")
    val oldPeak: String,
    val slope: String,
    @SerializedName("coronary_artery")
    val coronaryArtery: String,
    val thal: String,
    val result: Int,
    @SerializedName("created_at")
    val createdAt: Long
)