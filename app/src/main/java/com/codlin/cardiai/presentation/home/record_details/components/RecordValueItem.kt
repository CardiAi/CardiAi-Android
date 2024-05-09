package com.codlin.cardiai.presentation.home.record_details.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.codlin.cardiai.presentation.theme.CardiAiTheme

@Composable
fun RecordValueItem(
    name: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Text(
        buildAnnotatedString {
            withStyle(style = MaterialTheme.typography.titleMedium.toSpanStyle()) {
                append("$name: ")
            }
            withStyle(style = MaterialTheme.typography.titleSmall.toSpanStyle()) {
                append(value)
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun TextStylePreview() {
    CardiAiTheme {
        RecordValueItem("Hello", "Nadin")
    }
}