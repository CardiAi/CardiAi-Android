package com.codlin.cardiai.presentation.new_record.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codlin.cardiai.presentation.new_record.NumericQuestion
import com.codlin.cardiai.presentation.theme.CardiAiTheme

@Composable
fun NumericalQuestionItem(
    question: NumericQuestion,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start,
        modifier = modifier
    ) {
        Text(text = question.title, style = MaterialTheme.typography.bodyMedium)
        TextField(
            value = question.answer?.toString() ?: "",
            onValueChange = onValueChange,
            placeholder = { Text(question.placeholder) },
            suffix = { question.unit?.let { Text(text = it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun NumericalQuestionPreview() {
    CardiAiTheme {
    }
}