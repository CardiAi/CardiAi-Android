package com.codlin.cardiai.presentation.new_record.components

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codlin.cardiai.R
import com.codlin.cardiai.presentation.components.ThemeButton
import com.codlin.cardiai.presentation.new_record.NumericQuestion
import com.codlin.cardiai.presentation.theme.CardiAiTheme

@Composable
fun NumericalQuestionsPage(
    questionsList: List<NumericQuestion>,
    onQuestionValueChange: (index: Int, value: String) -> Unit,
    onConfirmClicked: () -> Unit,
) {
    Column(
        modifier = Modifier.scrollable(
            rememberScrollState(),
            orientation = Orientation.Vertical
        ),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        QuestionImage(resId = R.drawable.q6)
        questionsList.forEachIndexed { index, numericQuestion ->
            NumericalQuestionItem(
                question = numericQuestion,
                onValueChange = {
                    onQuestionValueChange(index, it)
                }
            )
        }
        ThemeButton(text = "Confirm", onClick = onConfirmClicked)
    }
}

@Preview
@Composable
private fun NumericalQuestionsPagePreview() {
    CardiAiTheme {
    }
}