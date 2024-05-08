package com.codlin.cardiai.presentation.home.patients_list.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.codlin.cardiai.presentation.theme.CardiAiTheme

@Composable
fun AddPatientButton(
    onClick: () -> Unit,
    expanded: Boolean = true
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        icon = {
            Icon(imageVector = Icons.Outlined.PersonAdd, contentDescription = null)
        },
        text = {
            Text(
                text = "Add Patient",
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center
            )
        },
        expanded = expanded,
    )
}

@Preview(showBackground = true)
@Composable
private fun AddPatientButtonPreview() {
    CardiAiTheme {
        AddPatientButton({})
    }
}