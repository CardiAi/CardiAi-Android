package com.codlin.cardiai.domain.model.record

data class Record(
    val id: Int?,
    val chestPain: ChestPain,
    val bloodPressure: Int?,
    val ecg: ECG?,
    val cholesterol: Int?,
    val bloodSugar: Int,
    val maxThal: Int,
    val exerciseAngina: Boolean,
    val oldPeak: Double,
    val slope: Slope,
    val coronaryArtery: Int,
    val thal: Thal,
    val result: Int?,
    val createdAt: String?,
    val patientId: Int? = null,
    val patientName: String? = null,
)