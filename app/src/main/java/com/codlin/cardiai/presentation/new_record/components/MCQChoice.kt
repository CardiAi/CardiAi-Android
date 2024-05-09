package com.codlin.cardiai.presentation.new_record.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codlin.cardiai.presentation.theme.CardiAiTheme
import com.codlin.cardiai.presentation.theme.Primary100
import com.codlin.cardiai.presentation.theme.Secondary100

@Composable
fun MCQChoice(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colorState = if (isSelected) Primary100 else Secondary100
    val color by animateColorAsState(targetValue = colorState, label = "ColorTransition")

    ElevatedCard(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = color,
            contentColor = Color.Black
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
            )
            AnimatedVisibility(
                visible = isSelected,
                label = "Selected Icon visibility",
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = "Selected"
                )
            }
        }
    }
}

@Preview
@Composable
private fun MCQChoiceSelectedOffPreview() {
    CardiAiTheme {
        var isSelected by remember {
            mutableStateOf(false)
        }
        MCQChoice(
            "Typical Angina", isSelected, { isSelected = !isSelected },
            Modifier
                .fillMaxWidth()
                .padding(32.dp)
        )
    }
}

@Preview
@Composable
private fun MCQChoiceSelectedOnPreview() {
    CardiAiTheme {
        MCQChoice(
            "Atypical Angina", true, {},
            Modifier
                .fillMaxWidth()
                .padding(32.dp)
        )
    }
}