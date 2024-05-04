package com.codlin.cardiai.presentation.auth.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codlin.cardiai.presentation.theme.Neutral600

@Composable
fun HorizontalOrLine() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        HorizontalDivider(modifier = Modifier.weight(1f), color = Neutral600)
        Text(
            text = "or",
            modifier = Modifier.padding(horizontal = 4.dp),
            color = Neutral600,
            style = MaterialTheme.typography.labelSmall,
        )
        HorizontalDivider(modifier = Modifier.weight(1f), color = Neutral600)
    }
}