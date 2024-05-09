package com.codlin.cardiai.presentation.new_record

import com.codlin.cardiai.domain.model.record.RecordType


data class NewRecordState(
    val currentPageIndex: Int = 0,
    val questions: List<Question<*>> = mcqQuestionsList,
    val numericalQuestions: List<NumericQuestion> = numericalQuestionsList,
    val navDestination: NewRecordDestination? = null,
)

sealed interface NewRecordDestination {
}

private val mcqQuestionsList = listOf(
    Question("What is the type of chest pain the patient feels?", RecordType.ChestPain.entries),
    Question("What are the resting electrocardiographic results?", RecordType.ECG.entries),
    Question("What is the slope of the peak exercise ST segment?", RecordType.Slope.entries),
    Question(
        "What would you classify the resulting condition of Thalassemia?",
        RecordType.Thal.entries
    ),
    Question("Does the patient suffer from Exercise Angina?", listOf("Yes", "No")),
)

private val numericalQuestionsList = listOf(
    NumericQuestion("What is the resting blood pressure?", "RBP", unit = "mm Hg"),
    NumericQuestion("What is the cholesterol measure?", "Cholesterol", unit = "mg / dl"),
    NumericQuestion("What is the maximum heart rate achieved?", "thal"),
    NumericQuestion(
        "What is the number of major vessels (0-3) colored by fluoroscopy?",
        "Coronary Artery"
    ),
    NumericQuestion("What is the fasting blood sugar?", "FBS", unit = "mg / dl"),
    NumericQuestion(
        "What is the ST depression induced by exercise relative to rest?",
        "Old Peak",
    ),
)