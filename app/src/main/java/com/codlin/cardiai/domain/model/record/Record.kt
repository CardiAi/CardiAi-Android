package com.codlin.cardiai.domain.model.record

data class Record(
    val id: Int? = null,
    val chestPain: ChestPain = ChestPain.non_anginal,
    val bloodPressure: Int? = null,
    val ecg: ECG? = null,
    val cholesterol: Int? = null,
    val bloodSugar: Int = 0,
    val maxThal: Int = 0,
    val exerciseAngina: Boolean = false,
    val oldPeak: Double = 0.0,
    val slope: Slope = Slope.flat,
    val coronaryArtery: Int = 0,
    val thal: Thal = Thal.normal,
    val result: Int? = null,
    val createdAt: String? = null,
    val patientId: Int? = null,
    val patientName: String? = null,
)