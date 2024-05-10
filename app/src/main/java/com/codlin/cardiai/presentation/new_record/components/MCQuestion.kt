package com.codlin.cardiai.presentation.new_record.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codlin.cardiai.presentation.UIFormatter
import com.codlin.cardiai.presentation.new_record.Question
import com.codlin.cardiai.presentation.theme.CardiAiTheme

@Composable
fun MCQuestion(
    question: Question<*>,
    @DrawableRes imageResId: Int,
    onSelectChoice: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .scrollable(
                rememberScrollState(),
                orientation = Orientation.Vertical
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        QuestionImage(resId = imageResId)
        Text(
            text = question.question,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            question.choices.forEachIndexed { index, choice ->
                MCQChoice(
                    text = UIFormatter.formatChoice(choice),
                    isSelected = index == question.answerIndex,
                    onClick = { onSelectChoice(index) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MCQuestionPreview() {
    CardiAiTheme {

    }
}