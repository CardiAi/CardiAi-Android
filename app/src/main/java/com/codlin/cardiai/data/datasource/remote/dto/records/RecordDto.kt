package com.codlin.cardiai.data.datasource.remote.dto.records

import com.codlin.cardiai.domain.model.Patient
import com.codlin.cardiai.domain.model.record.ECG
import com.codlin.cardiai.domain.model.record.Record
import com.google.gson.annotations.SerializedName

data class RecordDto(
    val id: Int?,
    @SerializedName("chest_pain")
    val chestPain: String,
    @SerializedName("blood_pressure")
    val bloodPressure: Int?,
    val ecg: String?,
    val cholesterol: Int?,
    @SerializedName("blood_sugar")
    val bloodSugar: Int,
    @SerializedName("max_thal")
    val maxThal: Int,
    @SerializedName("exercise_angina")
    val exerciseAngina: Int,
    @SerializedName("old_peak")
    val oldPeak: Double,
    val slope: String,
    @SerializedName("coronary_artery")
    val coronaryArtery: Int,
    val thal: String,
    val result: Int?,
    @SerializedName("created_at")
    val createdAt: String?,
    val patient: Patient?,
) {
    fun toDomainModel(): Record =
        Record(
            id = id,
            chestPain = stringToChestPain(chestPain),
            bloodPressure = bloodPressure,
            ecg = ecg?.let { stringToEcg(it) },
            cholesterol = cholesterol,
            bloodSugar = bloodSugar,
            maxThal = maxThal,
            exerciseAngina = exerciseAngina != 0,
            oldPeak = oldPeak,
            slope = stringToSlope(slope),
            coronaryArtery = coronaryArtery,
            thal = stringToThal(thal),
            result = result,
            createdAt = createdAt,
            patientId = patient?.id,
            patientName = patient?.name,
        )

    companion object {
        fun fromDomainModel(record: Record): RecordDto = record.run {
            RecordDto(
                id = id,
                chestPain = chestPain.name.replaceUnderscoreWithSpace(),
                bloodPressure = bloodPressure,
                ecg = when (ecg) {
                    ECG.unknown -> null
                    ECG.stt_abnormality -> "st-t abnormality"
                    else -> ecg?.name?.replaceUnderscoreWithSpace()
                },
                cholesterol = cholesterol,
                bloodSugar = bloodSugar,
                maxThal = maxThal,
                exerciseAngina = if (exerciseAngina) 1 else 0,
                oldPeak = oldPeak,
                slope = slope.name.replaceUnderscoreWithSpace(),
                coronaryArtery = coronaryArtery,
                thal = thal.name.replaceUnderscoreWithSpace(),
                result = result,
                createdAt = createdAt,
                patient = null
            )
        }
    }
}