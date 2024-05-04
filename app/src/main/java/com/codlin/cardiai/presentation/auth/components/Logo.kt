package com.codlin.cardiai.presentation.auth.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Logo(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .border(
                2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "CardiAi",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
        )
    }
}