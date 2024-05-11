package com.codlin.cardiai.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.codlin.cardiai.domain.model.Gender
import com.codlin.cardiai.domain.model.Patient
import com.codlin.cardiai.presentation.components.ThemeButton
import com.codlin.cardiai.presentation.components.ThemeTextField
import com.codlin.cardiai.presentation.theme.Secondary1000
import com.codlin.cardiai.presentation.theme.Secondary200

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    patient: Patient? = null,
    isVisible: Boolean,
    sheetState: SheetState,
    onNameChanged: (String) -> Unit,
    onAgeChanged: (String) -> Unit,
    onGenderChanged: (Gender) -> Unit,
    onSubmitClicked: () -> Unit,
    onDismiss: () -> Unit,
) {
    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = sheetState,
            shape = RoundedCornerShape(28.dp),
            containerColor = MaterialTheme.colorScheme.surface,
            windowInsets = WindowInsets(bottom = 150.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {
                ThemeTextField(
                    value = patient?.name ?: "",
                    onValueChange = onNameChanged,
                    label = "Name",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                ThemeTextField(
                    value = patient?.age?.toString() ?: "",
                    onValueChange = {
                        if (
                            it.isEmpty() ||
                            (it.isDigitsOnly() &&
                                    it.length <= 3)
                        ) {
                            onAgeChanged(it)
                        }
                    },
                    label = "Age",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                    )
                )
                Spacer(modifier = Modifier.height(32.dp))
                SingleChoiceSegmentedButtonRow {
                    val colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = Secondary200,
                        activeContentColor = Secondary1000,
                        inactiveContentColor = Secondary1000,
                        inactiveBorderColor = Secondary1000,
                        activeBorderColor = Secondary1000
                    )
                    SegmentedButton(
                        selected = patient?.gender == Gender.Male,
                        onClick = { onGenderChanged(Gender.Male) },
                        shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                        colors = colors
                    ) {
                        Text(text = "Male")
                    }
                    SegmentedButton(
                        selected = patient?.gender == Gender.Female,
                        onClick = { onGenderChanged(Gender.Female) },
                        shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                        colors = colors,
                    ) {
                        Text(text = "Female")
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                ThemeButton(text = "Submit", onClick = onSubmitClicked)
            }
        }
    }
}