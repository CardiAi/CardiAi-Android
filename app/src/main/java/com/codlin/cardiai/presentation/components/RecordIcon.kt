package com.codlin.cardiai.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codlin.cardiai.R
import com.codlin.cardiai.presentation.theme.CardiAiTheme
import com.codlin.cardiai.presentation.theme.HeartBackground

@Composable
fun RecordIcon(result: Int?, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(
                shape = CircleShape,
                color = HeartBackground,
            )
    ) {
        val resId = when(result){
            0 -> R.drawable.heart_healthy
            1 -> R.drawable.heart_degree1
            2 -> R.drawable.heart_degree2
            3 -> R.drawable.heart_degree3
            4 -> R.drawable.heart_degree4
            else -> R.drawable.heart_healthy
        }

        Image(
            painter = painterResource(id = resId),
            contentDescription = if (result == 0) "Healthy" else "Degree $result",
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun RecordIconPreview() {
    CardiAiTheme {
        RecordIcon(2, modifier = Modifier.size(64.dp))
    }
}