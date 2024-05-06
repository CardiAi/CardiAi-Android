package com.codlin.cardiai.presentation.home.patients_list.components

import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.codlin.cardiai.R
import com.codlin.cardiai.presentation.theme.CardiAiTheme
import com.codlin.cardiai.presentation.theme.Primary1000
import com.codlin.cardiai.presentation.theme.Primary200

@Composable
fun StartDiagnosisButton(
    onClick: () -> Unit,
    expanded: Boolean = true
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        containerColor = Primary200,
        contentColor = Primary1000,
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ecg_heart_icon),
                contentDescription = "ecg heart icon",
            )
        },
        text = {
            Text(
                text = "Start Diagnosis",
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center
            )
        },
        expanded = expanded,
    )
}

@Preview(showBackground = true)
@Composable
private fun StartDiagnosisButtonPreview() {
    CardiAiTheme {
        StartDiagnosisButton({})
    }
}