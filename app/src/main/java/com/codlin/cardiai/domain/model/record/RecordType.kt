package com.codlin.cardiai.domain.model.record

sealed interface RecordType {
    enum class ChestPain : RecordType { typical_angina, atypical_angina, non_anginal, asymptomatic }
    enum class ECG : RecordType { normal, sst_abnormality, lv_hypertrophy }
    enum class Slope : RecordType { flat, upsloping, downsloping }
    enum class Thal : RecordType { normal, reversible_defect, fixed_defect }
}