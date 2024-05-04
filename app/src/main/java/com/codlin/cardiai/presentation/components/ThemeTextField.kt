package com.codlin.cardiai.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun ThemeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions()
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label)
        },
        shape = RoundedCornerShape(4.dp),
        keyboardOptions = keyboardOptions,
        singleLine = true,
    )
}

@Composable
fun ThemePasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    onVisibilityIconClicked: () -> Unit,
    isPasswordVisible: Boolean,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Default,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = "Password")
        },
        shape = RoundedCornerShape(4.dp),
        trailingIcon = {
            IconButton(onClick = onVisibilityIconClicked) {
                AnimatedContent(
                    targetState = isPasswordVisible,
                    label = "Password Visibility"
                ) {
                    if (it)
                        Icon(
                            imageVector = Icons.Outlined.Visibility,
                            contentDescription = "Hide Password"
                        )
                    else
                        Icon(
                            imageVector = Icons.Outlined.VisibilityOff,
                            contentDescription = "Show Password"
                        )
                }
            }
        },
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        singleLine = true,
    )
}