package com.codlin.cardiai.presentation.new_record

import com.codlin.cardiai.domain.model.record.ChestPain
import com.codlin.cardiai.domain.model.record.ECG
import com.codlin.cardiai.domain.model.record.Record
import com.codlin.cardiai.domain.model.record.Slope
import com.codlin.cardiai.domain.model.record.Thal


data class NewRecordState(
    val currentPageIndex: Int = 0,
    val questions: List<Question<*>> = mcqQuestionsList,
    val numericalQuestions: List<NumericQuestion> = numericalQuestionsList,
    val screenError: String? = null,
    val navDestination: NewRecordDestination? = null,
)

sealed interface NewRecordDestination {
    data class RecordResultsDestination(val record: Record) : NewRecordDestination
    data object NavigateUp : NewRecordDestination
}

private val mcqQuestionsList = listOf(
    Question("What is the type of chest pain the patient feels?", ChestPain.entries),
    Question("What are the resting electrocardiographic results?", ECG.entries),
    Question("What is the slope of the peak exercise ST segment?", Slope.entries),
    Question(
        "What would you classify the resulting condition of Thalassemia?",
        Thal.entries
    ),
    Question("Does the patient suffer from Exercise Angina?", listOf("Yes", "No")),
)

private val numericalQuestionsList = listOf(
    NumericQuestion(
        "What is the resting blood pressure?",
        "RBP",
        unit = "mm Hg",
        minValue = 0.0,
        maxValue = 200.0
    ),
    NumericQuestion(
        "What is the cholesterol measure?",
        "Cholesterol",
        unit = "mg / dl",
        minValue = 0.0,
        maxValue = 603.0
    ),
    NumericQuestion(
        "What is the maximum heart rate achieved?",
        "Max Thal",
        minValue = 60.0,
        maxValue = 202.0,
        isRequired = true,
    ),
    NumericQuestion(
        "What is the number of major vessels (0-3) colored by fluoroscopy?",
        "Coronary Artery",
        minValue = 0.0,
        maxValue = 3.0,
        isRequired = true,
    ),
    NumericQuestion(
        "What is the fasting blood sugar?",
        "Fasting Blood Sugar",
        unit = "mg / dl",
        minValue = Double.MIN_VALUE,
        maxValue = Double.MAX_VALUE,
        isRequired = true,
    ),
    NumericQuestion(
        "What is the ST depression induced by exercise relative to rest?",
        "Old Peak",
        minValue = -2.6,
        maxValue = 6.2,
        isDecimal = true,
        isRequired = true,
    ),
)