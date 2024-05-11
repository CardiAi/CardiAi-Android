package com.codlin.cardiai.presentation.new_record.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.codlin.cardiai.R
import com.codlin.cardiai.presentation.components.ThemeButton
import com.codlin.cardiai.presentation.new_record.NumericQuestion

@Composable
fun NumericalQuestionsPage(
    questionsList: List<NumericQuestion>,
    onQuestionValueChange: (index: Int, value: String) -> Unit,
    onConfirmClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        QuestionImage(resId = R.drawable.q6)
        Spacer(modifier = Modifier.height(32.dp))
        questionsList.forEachIndexed { index, numericQuestion ->
            val doneImeAction = index == questionsList.size - 1
            NumericalQuestionItem(
                question = numericQuestion,
                onValueChange = {
                    onQuestionValueChange(index, it)
                },
                imeAction = if (doneImeAction) ImeAction.Done else ImeAction.Next
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        ThemeButton(text = "Confirm", onClick = onConfirmClicked)
    }
}