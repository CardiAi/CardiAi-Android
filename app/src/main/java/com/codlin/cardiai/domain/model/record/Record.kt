package com.codlin.cardiai.domain.model.record

data class Record(
    val id: Int?,
    val chestPain: RecordType.ChestPain,
    val bloodPressure: Int?,
    val ecg: RecordType.ECG?,
    val cholesterol: Int?,
    val bloodSugar: Int,
    val maxThal: Int,
    val exerciseAngina: Boolean,
    val oldPeak: Double,
    val slope: RecordType.Slope,
    val coronaryArtery: Int,
    val thal: RecordType.Thal,
    val result: Int?,
    val createdAt: String?,
)