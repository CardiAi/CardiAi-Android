package com.codlin.cardiai.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ThemeButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isPrimary: Boolean = true
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isPrimary) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.primaryContainer,
            contentColor = if (isPrimary) MaterialTheme.colorScheme.onPrimary
            else MaterialTheme.colorScheme.onPrimaryContainer
        ),
        contentPadding = PaddingValues(16.dp),
    ) {
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview
@Composable
private fun ThemeButtonPreview() {
    ThemeButton(
        "Login", {},
        Modifier
            .fillMaxSize()
            .padding(24.dp)
    )
}